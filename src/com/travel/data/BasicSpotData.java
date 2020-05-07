package com.travel.data;

import java.util.Vector;

public class BasicSpotData {

	private String area = "";
	private String subarea = "";

	private Spot spot;
	private SpotDetail spotDetail;

	private Vector<SpotType> spotType = new Vector<SpotType>();
	private Vector<Price> price = new Vector<Price>();
	private Vector<PriceSpecial> priceSpecial = new Vector<PriceSpecial>();
	private Vector<TimeOpenClose> open = new Vector<TimeOpenClose>();
	private Vector<TimeOpenClose> close = new Vector<TimeOpenClose>();
	private Vector<SpecialEvent> specialEvent = new Vector<SpecialEvent>();
	private Vector<SpecialClosed> specialClosed = new Vector<SpecialClosed>();
	private Vector<AccessInfo> access = new Vector<AccessInfo>();
	private Vector<SpotTimeTable> spotTable = new Vector<SpotTimeTable>();

	public Spot getSpot() {
		return spot;
	}

	public void setSpot(Spot spot) {
		this.spot = spot;
	}

	public SpotDetail getSpotDetail() {
		return spotDetail;
	}

	public void setSpotDetail(SpotDetail spotDetail) {
		this.spotDetail = spotDetail;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSubarea() {
		return subarea;
	}

	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}

	public Vector<SpotType> getSpotType() {
		return spotType;
	}

	public void addSpotType(SpotType spotType) {
		this.spotType.add(spotType);
	}

	public void setSpotType(Vector<SpotType> spotType) {
		this.spotType = spotType;
	}

	public Vector<Price> getPrice() {
		return price;
	}

	public void addPrice(Price price) {
		this.price.add(price);
	}

	public void setPrice(Vector<Price> price) {
		this.price = price;
	}

	public Vector<PriceSpecial> getPriceSpecial() {
		return priceSpecial;
	}

	public void addPriceSpecial(PriceSpecial priceSpecial) {
		this.priceSpecial.add(priceSpecial);
	}

	public void setPriceSpecial(Vector<PriceSpecial> priceSpecial) {
		this.priceSpecial = priceSpecial;
	}

	public Vector<TimeOpenClose> getBusinessHours() {
		return open;
	}

	public void addOpen(TimeOpenClose open) {
		this.open.add(open);
	}

	public void setOpen(Vector<TimeOpenClose> open) {
		this.open = open;
	}

	public Vector<TimeOpenClose> getClosed() {
		return close;
	}

	public void addClose(TimeOpenClose close) {
		this.close.add(close);
	}

	public void setClose(Vector<TimeOpenClose> close) {
		this.close = close;
	}

	public Vector<SpecialEvent> getSpecialEvent() {
		return specialEvent;
	}

	public void addSpecialEvent(SpecialEvent specialEvent) {
		this.specialEvent.add(specialEvent);
	}

	public void setSpecialEvent(Vector<SpecialEvent> specialEvent) {
		this.specialEvent = specialEvent;
	}

	public Vector<SpecialClosed> getSpecialClosed() {
		return specialClosed;
	}

	public void addSpecialClosed(SpecialClosed specialClosed) {
		this.specialClosed.add(specialClosed);
	}

	public void setSpecialClosed(Vector<SpecialClosed> specialClosed) {
		this.specialClosed = specialClosed;
	}

	public Vector<AccessInfo> getAccess() {
		return access;
	}

	public void addAccessInfo(AccessInfo access) {
		this.access.add(access);
	}

	public void setAccess(Vector<AccessInfo> access) {
		this.access = access;
	}

	public Vector<SpotTimeTable> getSpotTable() {
		return spotTable;
	}

	public void addSpotTable(SpotTimeTable spotTable) {
		this.spotTable.add(spotTable);
	}

	public void setSpotTable(Vector<SpotTimeTable> spotTable) {
		this.spotTable = spotTable;
	}

	public void setName(String name) {
		if (this.spot == null)
			this.spot = new Spot();
		this.spot.setName(name);
	}

	public String getName() {
		if (this.spot != null)
			return this.spot.getName();
		return null;
	}

	public void setType(String type) {
		if (this.spot == null)
			this.spot = new Spot();
		this.spot.setType(type);
	}

	public void setAddress(String text) {
		if (this.spot == null)
			this.spot = new Spot();
		this.spot.setAddress(text);
	}

	public void setCountry(String text) {
		if (this.spot == null)
			this.spot = new Spot();
		this.spot.setCountry(text);
	}

	public void setSource(String text) {
		if (this.spot == null)
			this.spot = new Spot();
		this.spot.setSource(text);
	}

	public String getSource() {
		if (this.spot != null)
			return this.spot.getSource();
		return null;
	}

	public void setInfo(String info) {
		if (this.spotDetail == null)
			this.spotDetail = new SpotDetail();
		this.spotDetail.setInfo(info);
	}

	public void setTel(String text) {
		if (this.spotDetail == null)
			this.spotDetail = new SpotDetail();
		this.spotDetail.setTel(text);
	}

	public void setUrl(String text) {
		if (this.spotDetail == null)
			this.spotDetail = new SpotDetail();
		this.spotDetail.setUrl(text);
	}

	public void setPlace(String text) {
		if (this.spotDetail == null)
			this.spotDetail = new SpotDetail();
		this.spotDetail.setPlace(text);
	}

	public void setParking(boolean value) {
		if (this.spotDetail == null)
			this.spotDetail = new SpotDetail();
		this.spotDetail.setParking(value);
	}

}
