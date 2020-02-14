import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayFieldValues {

		private static PlayFieldValues instance = null;

		private PlayFieldValues() {
		}

		public static PlayFieldValues getInstance() {
				synchronized (instance) {
						if (instance == null) {
								instance = new PlayFieldValues();
						}
				}
				return instance;
		}

		private final List<Integer> play9x9 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
		private final List<Integer> playHex = List.of(0, 1,2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
		private final List<Integer> play25 = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25);

		public Integer rootPlayField(PlayList playList){
			switch (playList) {
				case play9x9:	return 3;
				case playHex: return 4;
			case play25: return 5;
				default:	throw new IllegalArgumentException("Illegal argument in rootPlayField");
			}
		}

		public List<Integer> getPlayFieldValues(PlayList playList) {
				return List.copyOf(playList == PlayList.play9x9 ? play9x9 : playHex);
		}

		public boolean playFieldContains(PlayList playList,Integer item) {
				return getPlayFieldValues(playList).contains(item);
		}

		public List<Integer> filterItems(List<Integer> itemsToFilter, PlayList playList) {
				List<Integer> result = new ArrayList<>();
				List<Integer> filter = getPlayFieldValues(playList);
				for (Integer item : itemsToFilter) {
						if (filter.contains(item) && !result.contains(item)) {
								result.add(item);
						}
				}
				return result;
		}

		public String itemsAsString(List<Integer> items,PlayList playList) {
				String result = "";
				if (List.of(play9x9,playHex).contains(playList) ) {
						items = filterItems(items, playList);
						items.stream().sorted(Comparator.naturalOrder()).forEach(item -> result.concat(Integer.toHexString(item)));
				}
				if (List.of(play25).contains(play25)) {
						// ToDo implemented this
				}
				return  result;
		}
}
