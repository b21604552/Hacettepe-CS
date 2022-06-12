
public class Players implements Comparable<Players>{
	String name;
	int point;
	
	Players(String name, int point){
			this.name = name;
			this.point = point;
		  }

	@Override
	public int compareTo(Players player) {
		Integer point = new Integer(this.point);
		Integer pPoint = new Integer(player.point);
		int last = pPoint.compareTo(point);
		if (last == 0){
			return compareToString(player);
		}else if (last > 0){
			return last;
		}else{
			return pPoint.compareTo(point);
		}
	}
	
	public int compareToString(Players player) {
		int last = this.name.compareTo(player.name);
	    return last == 0 ? this.name.compareTo(player.name) : last;
	}

}
