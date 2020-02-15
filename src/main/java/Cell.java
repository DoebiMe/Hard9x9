import java.util.ArrayList;
import java.util.List;

/**
 * Hard, aggressive  approach
 *
 * 		The target is, eliminate all fault info,
 * 		e.g. 	there can not be candidates when there is an original or working value.
 * 		e.g.	incoming data is filtered to avoid duplicates in the list or not allowed values
 * 		e.g.	you can not change working value if	there is an original value
 * 		e.g.	if possible, prevent wrong returns by anticipate by cleaning candidates when calling for by ex. a count
 * 		1) you can not add candidates if there is an working value
 * 		2) you can not change working value if there is an original value
 * 	  3) setting an working value deletes all candidates
 * 	  4) setting an original value overwrites working value and deletes all candidates
 * 	  5) getDisplayValue() returns a String
 * 	  		if there is an value (original value or working value), this is the return
 * 	  	  if not, if there is only 1 candidate then return that 1 candidate
 * 	  	  if not, return empty string
 * 	  6) getDisplaycandidates() returns a String
 * 	  		if there is an value, (original value or working value), return empty string
 * 	  	  if not, if there are no candidates or just 1 candidate , return empty string*
 * 	  	  it not, return all candidates concatenated together
 * 	  7) getvalue()
 * 	  		if there is an original value, this is the return
 * 	  	  if not return the working value
 * 	  8) setworkingvalue()
 * 	  		if there is an original value, request is denied
 * 	  	  always deletes all the candidates
 * 	  9) setoriginalvalue()
 * 	  		overwrites the orignal value,overwrites the working value
 * 	  	  always deletes all the candidates
 * 	  10) countcandidates()
 * 	  		if there is an original or working value, deletes all candidates and returns 0
 * 	  	  if not, return the count of of candidates
 * 	  11) addcdandidates()
 * 	  		if there is an original or working value, deletes all candidates and request is denied
 * 	  		if not, all filtered candidates are added to the list
 * 	  12)	getAllCandidate()
 * 	  		if there is an original or working value, deletes all candidates and return empty list
 * 	  	  if not, return list
 * 	  13) containsallcandidates()
 * 	  		if there is an original or working value, deletes all candidates and returns false
 * 	  	  if not ,if all given candidates, filtered, are in the candidate list, returns true
 * 	  	  if not , returns false;
 * 	  14)	interpreting values
 * 	  		when an value = (NOT_COMPLETED), the value is not been set (yet)
 * 	  	  values can be NOT_COMPLETED or any number in the PlayFieldValues (with can start by 0 or by 1 ! ... depending on playlist)
 * 	  15) playfield values
 * 	  		playfieldvalues are depended on playList field
 * 	  		if playList is PlayList.play9x9, playfieldvalues can be 1,2,3,4,5,6,7,8,9
 * 	  		if playList is PlayList.playHex, playfieldvalues can be 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
 * 	  16) all incoming values (list or single value) is filterd according to the playlist value
 * 	  		meaning : 	a) no duplicate values
 * 	  								b) List is can only contain values set in the playfieldvalues
 *
 */

public class Cell {
		private List<Integer> candidateList;
		private Integer originalValue;
		private Integer workingValue;
		private RowCol rowCol;
		private Object component;
		private PlayList playList;



		public Cell(RowCol rowCol, Object component,PlayList playList) {
				this.rowCol = rowCol;
				this.component = component;
				this.playList = playList;
				candidateList = new ArrayList<>();
				workingValue = NOT_COMPLETED;
				originalValue = NOT_COMPLETED;
		}

		public String getDisplayValue() {
				if (getValue() != NOT_COMPLETED) return Integer.toString(getValue());
				if (countCandidates() == 1) return Integer.toString(candidateList.get(0));
				return "";
		}
		public String getDisplayCandidates() {
				if (countCandidates() < 2) return "";
				String result = "";
				for (Integer candidate : candidateList) {
						result += Integer.toString(candidate);
				}
				return result;
		}

		public Integer getValue() {
				if (originalValue != NOT_COMPLETED) return originalValue;
				return workingValue;
		}

		public void setOriginalValue(Integer value) {
				if (!PlayFieldValues.getInstance().playFieldContains(playList,value)) {
						throw new IllegalArgumentException("Illegal value in setOriginalValue");
				}
				originalValue = value;
				workingValue = value;
				candidateList.clear();
		}
		public void setWorkingValue(Integer value) {
				if (!PlayFieldValues.getInstance().playFieldContains(playList,value)) {
						throw new IllegalArgumentException("Illegal value in setWorkingValue");
				}
				if (originalValue == NOT_COMPLETED) {
						workingValue = value;
				}
				candidateList.clear();
		}


		public List<Integer> getAllCandidates() {
				if (getValue() != NOT_COMPLETED) {
						candidateList.clear();
				}
				return List.copyOf(candidateList);
		}
		public  void  removeAllCandidates() {
				candidateList.clear();
		}
		public void removeCandidates(List<Integer> candidatesToRemove) {
			if (candidatesToRemove==null || candidateList==null) return;
			candidatesToRemove = PlayFieldValues.getInstance().filterItems(candidatesToRemove,playList);
			for (Integer candidate :candidateList) {
					if (candidatesToRemove.contains(candidate)) {
							candidateList.remove(candidate);
					}
			}
		}
		public void addAllCandidates() {
				if (getValue() != NOT_COMPLETED) {
						candidateList.clear();
						return;
				}
				candidateList = PlayFieldValues.getInstance().getPlayFieldValues(playList);
		}
		public void addCandidates(List<Integer> candidatesToAdd) {
				if (getValue() != NOT_COMPLETED) {
						candidateList.clear();
						return;
				}
				candidatesToAdd = PlayFieldValues.getInstance().filterItems(candidatesToAdd,playList);
				candidatesToAdd.forEach(candidateToAdd -> {
						if (!candidateList.contains(candidateToAdd)) {
								candidateList.add(candidateToAdd);
						}
				});
		}
		public boolean containsAllCandidates(List<Integer> candidatesToCheck) {
			if (getValue() != NOT_COMPLETED) {
					candidateList.clear();
					return false;
			}
			candidatesToCheck = PlayFieldValues.getInstance().filterItems(candidatesToCheck,playList);
			for(Integer candidateToCheck : candidatesToCheck)  {
					if (!candidateList.contains(candidateToCheck)) {
							return false;
					}
			}
			return true;
		}

		public Integer countCandidates() {
				if (getValue() != NOT_COMPLETED) {
						candidateList.clear();
						return 0;
				}
				return candidateList.size();
		}


}
