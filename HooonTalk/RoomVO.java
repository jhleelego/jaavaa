package HooonTalk;

import java.util.ArrayList;

public class RoomVO<StudentVO> {
	private String roomName = "";
	private String roomAdmin = "";
	private int roomPlayCount = 0;
	ArrayList<StudentVO> al_StudentVO = new ArrayList<>();
	public RoomVO() {
	}
	
	public RoomVO(String roomName, String roomAdmin, int count) {
		this.roomName = roomName;
		this.roomAdmin = roomAdmin;
		this.roomPlayCount = count;
	}
	public void insert(StudentVO student) {
			al_StudentVO.add(student);
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomAdmin() {
		return roomAdmin;
	}
	public void setRoomAdmin(String roomAdmin) {
		this.roomAdmin = roomAdmin;
	}
	public int getRoomPlayCount() {
		return roomPlayCount;
	}
	public void setRoomPlayCount(int roomPlayCount) {
		this.roomPlayCount = roomPlayCount;
	}
	@Override
	public String toString() {
		String result = al_StudentVO.toString();
		return result;
	}
}
