package sol.workers.calendar.business.repository.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.asm.SpringAsmInfo;
import org.springframework.stereotype.Repository;

import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.business.comparator.WorkerComparator;
import sol.workers.calendar.business.repository.WorkerFileRepository;

/**
 * Implementation of the worker repository
 * 
 * @author safeki
 *
 */
@Repository
public class WorkerFileRepositoryImpl implements WorkerFileRepository {

	@Override
	public List<WorkerModel> readData(InputStream workersSourceIS) {
		List<WorkerModel> workers = new ArrayList<WorkerModel>();
		try {
			InputStreamReader isr = new InputStreamReader(workersSourceIS);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				String[] workerRow = line.split(",");
				if (workerRow == null) {
					System.out.println("Fichier vide !");
					System.exit(-2);
				}
				workers.add(new WorkerModel(workerRow));
			}
			br.close();
			workersSourceIS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Collections.sort(workers, new WorkerComparator());

		return workers;
	}

	@Override
	public void updateWorkersRestDay(WorkerModel[] workersFixedRestDay, String workersSourceFile) {
		SpringAsmInfo s;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		PrintWriter writer = null;
		try {
			is = new FileInputStream(workersSourceFile);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			writer = new PrintWriter(workersSourceFile + ".temp", "UTF-8");

			String line;
			while ((line = br.readLine()) != null) {
				String[] workerRow = line.split(",");
				for (WorkerModel worker : workersFixedRestDay) {
					if (worker != null) {
						if (workerRow[0].trim().equals(worker.getFirstName())
								&& workerRow[1].trim().equals(worker.getLastName())) {
							workerRow[3] = " " + worker.getRestDay();
						}
					}
				}
				writer.println(StringUtils.join(workerRow, ","));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			writer.close();
			try {
				is.close();
				isr.close();
				br.close();
				File oldStaffFile = new File(workersSourceFile);
				oldStaffFile.delete();
				File newStaffFile = new File(workersSourceFile + ".temp");
				newStaffFile.renameTo(new File(workersSourceFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
