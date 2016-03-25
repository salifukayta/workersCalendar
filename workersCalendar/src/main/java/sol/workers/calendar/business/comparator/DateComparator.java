package sol.workers.calendar.business.comparator;

import java.util.Comparator;

public class DateComparator implements Comparator<String> {

	@Override
	public int compare(String date1, String date2) {
		if (date1.equals(date2)) {
			return 0;
		}
		if (date1.equals("Lundi")) {
			return -1;
		}
		if (date2.equals("Lundi")) {
			return 1;
		}
		if (date1.equals("Mardi")) {
			return -1;
		}
		if (date2.equals("Mardi")) {
			return 1;
		}
		if (date1.equals("Mercredi")) {
			return -1;
		}
		if (date2.equals("Mercredi")) {
			return 1;
		}
		if (date1.equals("Jeudi")) {
			return -1;
		}
		if (date2.equals("Jeudi")) {
			return 1;
		}
		if (date1.equals("Vendredi")) {
			return -1;
		}
		if (date2.equals("Vendredi")) {
			return 1;
		}
		if (date1.equals("Samedi")) {
			return -1;
		}
		if (date2.equals("Samedi")) {
			return 1;
		}
		if (date1.equals("Dimanche")) {
			return -1;
		}
		if (date2.equals("Dimanche")) {
			return 1;
		}
		return 0;
	}
}
