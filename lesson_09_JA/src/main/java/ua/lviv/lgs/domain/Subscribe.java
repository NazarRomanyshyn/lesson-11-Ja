package ua.lviv.lgs.domain;

import java.time.LocalDate;

public class Subscribe {
	private int id;
	private int userID;
	private int magazineID;
	private boolean subscribeStatus;
	private LocalDate subscribeDate;
	private int subscribePeriod;

	public Subscribe(int id, int userID, int magazineID, boolean subscribeStatus, LocalDate subscribeDate,
			int subscribePeriod) {
		this.id = id;
		this.userID = userID;
		this.magazineID = magazineID;
		this.subscribeStatus = subscribeStatus;
		this.subscribeDate = subscribeDate;
		this.subscribePeriod = subscribePeriod;
	}

	public Subscribe(int userID, int magazineID, boolean subscribeStatus, LocalDate subscribeDate,
			int subscribePeriod) {
		this.userID = userID;
		this.magazineID = magazineID;
		this.subscribeStatus = subscribeStatus;
		this.subscribeDate = subscribeDate;
		this.subscribePeriod = subscribePeriod;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getMagazineID() {
		return magazineID;
	}

	public void setMagazineID(int magazineID) {
		this.magazineID = magazineID;
	}

	public boolean getSubscribeStatus() {
		return subscribeStatus;
	}

	public void setSubscribeStatus(boolean subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	public LocalDate getSubscribeDate() {
		return subscribeDate;
	}

	public void setSubscribeDate(LocalDate subscribeDate) {
		this.subscribeDate = subscribeDate;
	}

	public int getSubscribePeriod() {
		return subscribePeriod;
	}

	public void setSubscribePeriod(int subscribePeriod) {
		this.subscribePeriod = subscribePeriod;
	}

	@Override
	public String toString() {
		if (id == 0)
			return "Підписка [користувачID=" + userID + ", журналID=" + magazineID + ", статус підписки="
			+ subscribeStatus + ", дата підписки=" + subscribeDate + ", період підписки=" + subscribePeriod
			+ "]";
else
	return "Підписка [id=" + id + ", користувачID=" + userID + ", журналID=" + magazineID + ", статус підписки="
			+ subscribeStatus + ", дата підписки=" + subscribeDate + ", період підписки=" + subscribePeriod
			+ "]";
	}

}
