package sol.workers.calendar.business.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQueryFactory;

import sol.workers.calendar.business.PossiblePostsEnum;
import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.business.exceptions.FunctionalException;
import sol.workers.calendar.business.model.PostDTO;
import sol.workers.calendar.business.model.QPost;
import sol.workers.calendar.business.model.QVacation;
import sol.workers.calendar.business.model.QWorker;
import sol.workers.calendar.business.model.VacationDTO;
import sol.workers.calendar.business.model.WorkerDTO;
import sol.workers.calendar.business.repository.WorkerRepository;

/**
 * Implementation of the worker repository
 * 
 * @author safeki
 *
 */
@Repository
@Transactional
public class WorkerRepositoryImpl implements WorkerRepository {

	private static final String MSG_MANDATORY_WORKER_DATA = "\n\tVeuillez saisir les nom et prénom des ouvriers\n\n\tet leurs attribuer au moins un poste.";

	@Autowired
	private SQLQueryFactory sqlQueryFactory;

	private final QBean<WorkerDTO> workerModelBean = Projections.bean(WorkerDTO.class, QWorker.Worker.all());
	private final QBean<PostDTO> postModelBean = Projections.bean(PostDTO.class, QPost.Post.all());
	private final QBean<VacationDTO> vacationModelBean = Projections.bean(VacationDTO.class, QVacation.Vacation.all());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WorkerDTO getWorker(Integer workerId) {
		return sqlQueryFactory.select(workerModelBean).from(QWorker.Worker).where(QWorker.Worker.id.eq(workerId)).fetchOne();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<WorkerModel> getWorkers() {
		// List<WorkerModel> fetch = sqlQueryFactory.select(workerModelBean)
		// .leftJoin(QPost.Post).from(QWorker.Worker).on(QWorker.Worker.id.eq(QPost.Post.idWorker))
		// .fetch();
		List<WorkerDTO> WorkerDtoList = sqlQueryFactory.select(workerModelBean).from(QWorker.Worker).fetch();
		List<WorkerModel> workerModelList = new ArrayList<>();
		for (WorkerDTO WorkerDto : WorkerDtoList) {

			List<PostDTO> postDtoList = sqlQueryFactory.select(postModelBean).from(QPost.Post)
					.where(QPost.Post.idWorker.eq(WorkerDto.getId())).fetch();
			List<PossiblePostsEnum> possiblesPosts = new ArrayList<>();
			for (PostDTO postDTO : postDtoList) {
				possiblesPosts.add(PossiblePostsEnum.valueOf(postDTO.getPostName()));
			}

			List<VacationDTO> vacationDtoList = sqlQueryFactory.select(vacationModelBean).from(QVacation.Vacation)
					.where(QVacation.Vacation.idWorker.eq(WorkerDto.getId())).fetch();
			Boolean[] vacations = { false, false, false, false, false, false, false };
			for (VacationDTO vacationDTO : vacationDtoList) {
				vacations[vacationDTO.getDayIndex()] = true;
			}

			workerModelList.add(new WorkerModel(WorkerDto, possiblesPosts, vacations));
		}
		return workerModelList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws FunctionalException
	 */
	@Override
	public void editWorker(WorkerModel workerModel) throws FunctionalException {
		if (workerModel.getId() == null || getWorker(workerModel.getId()) == null) {
			addWorker(workerModel);
		} else if (workerModel.isDeleted()) {
			deleteWorker(workerModel);
		} else {
			updateWorker(workerModel);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws FunctionalException
	 */
	@Override
	public void updateWorker(WorkerModel workerModel) throws FunctionalException {

		checkWorkerMandatoryData(workerModel);

		sqlQueryFactory.update(QWorker.Worker).populate(workerModel).where(QWorker.Worker.id.eq(workerModel.getId())).execute();

		sqlQueryFactory.delete(QPost.Post).where(QPost.Post.idWorker.eq(workerModel.getId())).execute();
		for (PossiblePostsEnum possiblePostsEnum : workerModel.getPossiblesPosts()) {
			PostDTO postDTO = new PostDTO();
			postDTO.setIdWorker(workerModel.getId());
			postDTO.setPostName(possiblePostsEnum.name());
			sqlQueryFactory.insert(QPost.Post).populate(postDTO).execute();
		}

		sqlQueryFactory.delete(QVacation.Vacation).where(QVacation.Vacation.idWorker.eq(workerModel.getId())).execute();
		int dayIndex = 0;
		for (Boolean dayIndexVacation : workerModel.getVacations()) {
			if (dayIndexVacation) {
				VacationDTO vacationDTO = new VacationDTO();
				vacationDTO.setIdWorker(workerModel.getId());
				vacationDTO.setDayIndex(dayIndex);
				sqlQueryFactory.insert(QVacation.Vacation).populate(vacationDTO).execute();
			}
			dayIndex++;
		}
	}

	private void checkWorkerMandatoryData(WorkerModel workerModel) throws FunctionalException {
		if (workerModel.getFirstName() == null || workerModel.getFirstName().equals("") || workerModel.getLastName() == null
				|| workerModel.getLastName().equals("") || workerModel.getPossiblesPosts().size() < 1) {
			throw new FunctionalException(MSG_MANDATORY_WORKER_DATA);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteWorker(WorkerModel workerModel) {
		sqlQueryFactory.delete(QPost.Post).where(QPost.Post.idWorker.eq(workerModel.getId())).execute();
		sqlQueryFactory.delete(QVacation.Vacation).where(QVacation.Vacation.idWorker.eq(workerModel.getId())).execute();
		sqlQueryFactory.delete(QWorker.Worker).where(QWorker.Worker.id.eq(workerModel.getId())).execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws FunctionalException
	 */
	@Override
	public void addWorker(WorkerModel workerModel) throws FunctionalException {

		checkWorkerMandatoryData(workerModel);

		Integer workerId = sqlQueryFactory.insert(QWorker.Worker).populate(workerModel).executeWithKey(QWorker.Worker.id);
		workerModel.setId( workerId);
		
		for (PossiblePostsEnum possiblePostsEnum : workerModel.getPossiblesPosts()) {
			PostDTO postDTO = new PostDTO();
			postDTO.setIdWorker(workerModel.getId());
			postDTO.setPostName(possiblePostsEnum.name());
			sqlQueryFactory.insert(QPost.Post).populate(postDTO).execute();
		}

		int dayIndex = 0;
		for (Boolean dayIndexVacation : workerModel.getVacations()) {
			if (dayIndexVacation) {
				VacationDTO vacationDTO = new VacationDTO();
				vacationDTO.setIdWorker(workerModel.getId());
				vacationDTO.setDayIndex(dayIndex);
				sqlQueryFactory.insert(QVacation.Vacation).populate(vacationDTO).execute();
			}
			dayIndex++;
		}
	}

	@Override
	public void deleteAllWorkers() {
		sqlQueryFactory.delete(QPost.Post).execute();
		sqlQueryFactory.delete(QVacation.Vacation).execute();
		sqlQueryFactory.delete(QWorker.Worker).execute();
		
	}
}
