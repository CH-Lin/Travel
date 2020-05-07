package com.travel.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.travel.data.Access;
import com.travel.data.AccessInfo;
import com.travel.data.City;
import com.travel.data.Comment;
import com.travel.data.Counter;
import com.travel.data.Country;
import com.travel.data.GeoData;
import com.travel.data.Price;
import com.travel.data.PriceSpecial;
import com.travel.data.SpecialClosed;
import com.travel.data.SpecialEvent;
import com.travel.data.Spot;
import com.travel.data.SpotDetail;
import com.travel.data.SpotTimeTable;
import com.travel.data.TimeOpenClose;
import com.travel.data.SpotType;

public class DatabaseSession {

	private SqlSessionFactory sqlSessionFactory;

	public DatabaseSession() {
		sqlSessionFactory = DBConnectionFactory.getSqlSessionFactory();
	}

	/**
	 * Create all tables and indexes in the database.
	 */
	public void createTables() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.selectList("SpotMapper.createSpotTables_ps");
		} finally {
			session.close();
		}
	}

	/**
	 * Drop all tables and indexes in the database.
	 */
	public void dropTables() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.selectList("SpotMapper.dropSpotTables");
		} finally {
			session.close();
		}
	}

	/**
	 * Perform specail actions.
	 */
	public void spAction() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.selectList("SpotMapper.spAction");
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all table name from the database.
	 * 
	 * @return the list of all table name from the database.
	 */
	public List<String> listTables() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<String> list = session.selectList("SpotMapper.listTables");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of Counter into the database.
	 * 
	 * @param counter
	 *            the instance to be persisted.
	 */
	public void addCounter(Counter counter) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToCounter", counter);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns a Counter instance from the database.
	 * 
	 * @param key
	 *            primary key value used for lookup.
	 * 
	 * @return A Counter instance with a primary key value equals to key. null
	 *         if there is no matching row.
	 */
	public synchronized Counter getCounterByKey(String key) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			Counter counter = (Counter) session.selectOne(
					"SpotMapper.getCounterByKey", key);
			return counter;
		} finally {
			session.close();
		}
	}

	/**
	 * Update a Counter instance in the database.
	 * 
	 * @param counter
	 *            The counter must be updated
	 * 
	 */
	public synchronized void updateCounter(Counter counter) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.update("SpotMapper.updateCounterByKey", counter);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of Country into the database.
	 * 
	 * @param country
	 *            the instance to be persisted.
	 */
	public void addCountry(Country country) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToCountry", country);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all Country instances from the database.
	 * 
	 * @return the list of all Country instances from the database.
	 */
	public List<Country> getAllCountry() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<Country> list = session.selectList("SpotMapper.getAllCountry");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of City into the database.
	 * 
	 * @param city
	 *            the instance to be persisted.
	 */
	public void addCity(City city) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToCity", city);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all City instances from the database.
	 * 
	 * @return the list of all City instances from the database.
	 */
	public List<City> getAllCity() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<City> list = session.selectList("SpotMapper.getAllCity");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of Spot into the database.
	 * 
	 * @param spot
	 *            the instance to be persisted.
	 */
	public long addSpot(Spot spot) {

		long key = -1;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToSopt", spot);
			session.commit();
			key = spot.getSpotId();
		} finally {
			session.close();
		}
		return key;
	}

	/**
	 * 
	 */
	public long getSpotCount() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			long count = session.selectOne("SpotMapper.getSpotCount");
			return count;
		} finally {
			session.close();
		}
	}

	/**
	 * Returns a Spot instance from the database.
	 * 
	 * @param id
	 *            primary key value used for lookup.
	 * @return A Spot instance with a primary key value equals to id. null if
	 *         there is no matching row.
	 */
	public Spot getSpotById(long id) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			Spot contact = (Spot) session.selectOne("SpotMapper.getBySpotId",
					id);
			return contact;
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all SPOT instances from the database.
	 * 
	 * @return the list of all SPOT instances from the database.
	 */
	public List<Spot> getAllSpot() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<Spot> list = session.selectList("SpotMapper.getAllSpot");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the number of count of SPOT instances from the database from
	 * offset.
	 *
	 * @param count
	 *            number of SPOT instances
	 * @param offset
	 *            from offset
	 * @return the list of SPOT instances from the database.
	 */
	public List<Spot> getSpotWithOffset(long count, long offset) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			Map<String, Long> map = new HashMap<String, Long>();
			map.put("count", count);
			map.put("offset", offset);
			List<Spot> list = session.selectList("SpotMapper.getSpot", map);
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Updates an instance of Spot in the database.
	 * 
	 * @param spot
	 *            the instance to be updated.
	 */
	public void update(Spot spot) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.update("SpotMapper.update", spot);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Delete an instance of Spot from the database.
	 * 
	 * @param id
	 *            primary key value of the instance to be deleted.
	 */
	public void deleteSpotById(long id) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.delete("SpotMapper.deleteBySpotId", id);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of SpotDetail into the database.
	 * 
	 * @param spotDetail
	 *            the instance to be persisted.
	 */
	public void addSpotDetail(SpotDetail spotDetail) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToSoptDetail", spotDetail);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all SPOT instances from the database.
	 * 
	 * @return the list of all SPOT instances from the database.
	 */
	public List<SpotDetail> getAllSpotDetail() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<SpotDetail> list = session
					.selectList("SpotMapper.getAllSpotDetail");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of Price into the database.
	 * 
	 * @param price
	 *            the instance to be persisted.
	 */
	public void addPrice(Price price) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToPrice", price);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all Price instances from the database.
	 * 
	 * @return the list of all Price instances from the database.
	 */
	public List<Price> getAllPrice() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<Price> list = session.selectList("SpotMapper.getAllPrice");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of PriceSpecial into the database.
	 * 
	 * @param priceSpecial
	 *            the instance to be persisted.
	 */
	public void addPriceSpecial(PriceSpecial priceSpecial) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToPriceSpecial", priceSpecial);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all Price instances from the database.
	 * 
	 * @return the list of all Price instances from the database.
	 */
	public List<PriceSpecial> getAllPriceSpecial() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<PriceSpecial> list = session
					.selectList("SpotMapper.getAllPriceSpecial");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of TimeOpenClose into the database.
	 * 
	 * @param time
	 *            the instance to be persisted.
	 */
	public void addBusinessHours(TimeOpenClose time) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToBusinessHours", time);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all BusinessHours instances from the database.
	 * 
	 * @return the list of all BusinessHours instances from the database.
	 */
	public List<TimeOpenClose> getAllBusinessHours() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<TimeOpenClose> list = session
					.selectList("SpotMapper.getAllBusinessHours");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of TimeOpenClose into the database.
	 * 
	 * @param time
	 *            the instance to be persisted.
	 */
	public void addClosed(TimeOpenClose time) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToClosed", time);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all Closed instances from the database.
	 * 
	 * @return the list of all Closed instances from the database.
	 */
	public List<TimeOpenClose> getAllClosed() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<TimeOpenClose> list = session
					.selectList("SpotMapper.getAllClosed");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of SpecialEvent into the database.
	 * 
	 * @param event
	 *            the instance to be persisted.
	 */
	public void addSpecialEvent(SpecialEvent event) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToSpecialEvent", event);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all SpecialEvent instances from the database.
	 * 
	 * @return the list of all SpecialEvent instances from the database.
	 */
	public List<SpecialEvent> getAllSpecialEvent() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<SpecialEvent> list = session
					.selectList("SpotMapper.getAllSpecialEvent");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of SpecialClosed into the database.
	 * 
	 * @param close
	 *            the instance to be persisted.
	 */
	public void addSpecialClosed(SpecialClosed close) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToSpecialClosed", close);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all SpecialClosed instances from the database.
	 * 
	 * @return the list of all SpecialClosed instances from the database.
	 */
	public List<SpecialClosed> getAllSpecialClosed() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<SpecialClosed> list = session
					.selectList("SpotMapper.getAllSpecialClosed");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of SpotTimeTable into the database.
	 * 
	 * @param table
	 *            the instance to be persisted.
	 */
	public void addSpotTimeTable(SpotTimeTable table) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToSpotTimeTable", table);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all SpotTimeTable instances from the database.
	 * 
	 * @return the list of all SpotTimeTable instances from the database.
	 */
	public List<SpotTimeTable> getAllSpotTimeTable() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<SpotTimeTable> list = session
					.selectList("SpotMapper.getAllSpotTimeTable");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of Access into the database.
	 * 
	 * @param access
	 *            the instance to be persisted.
	 */
	public void addAccess(Access access) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToAccess", access);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all Access instances from the database.
	 * 
	 * @return the list of all Access instances from the database.
	 */
	public List<Access> getAllAccess() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<Access> list = session.selectList("SpotMapper.getAllAccess");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the Access instance from the database.
	 * 
	 * @return the Access instance from the database.
	 */
	public Access getAccess(long from, long to) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			Map<String, Long> map = new HashMap<String, Long>();
			map.put("from", from);
			map.put("to", to);
			Access access = session.selectOne("SpotMapper.getAccess", map);
			return access;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of AccessInfo into the database.
	 * 
	 * @param accessInfo
	 *            the instance to be persisted.
	 */
	public void addAccessInfo(AccessInfo accessInfo) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToAccessInfo", accessInfo);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all AccessInfo instances from the database.
	 * 
	 * @return the list of all AccessInfo instances from the database.
	 */
	public List<AccessInfo> getAllAccessInfo() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<AccessInfo> list = session
					.selectList("SpotMapper.getAllAccessInfo");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of Comment into the database.
	 * 
	 * @param comment
	 *            the instance to be persisted.
	 */
	public void addComment(Comment comment) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToComment", comment);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all Comment instances from the database.
	 * 
	 * @return the list of all Comment instances from the database.
	 */
	public List<Comment> getAllComment() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<Comment> list = session.selectList("SpotMapper.getAllComment");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of SpotType into the database.
	 * 
	 * @param type
	 *            the instance to be persisted.
	 */
	public void addSpotType(SpotType type) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToSpotType", type);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all SpotType instances from the database.
	 * 
	 * @return the list of all SpotType instances from the database.
	 */
	public List<SpotType> getAllSpotType() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<SpotType> list = session
					.selectList("SpotMapper.getAllSpotType");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of GeoData into the database.
	 * 
	 * @param geoData
	 *            the instance to be persisted.
	 */
	public void addGeoData(GeoData geoData) {

		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.insert("SpotMapper.insertToGeoData", geoData);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the GeoData instance from the database by key.
	 * 
	 * @return the GeoData instance from the database by key.
	 */
	public GeoData getGeoDataFromKey(String key) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			GeoData data = session.selectOne("SpotMapper.getGeoDataByKey", key);
			return data;
		} finally {
			session.close();
		}
	}

	/**
	 * Update a GeoData instance in the database.
	 * 
	 * @param geoData
	 *            The GeoData must be updated
	 * 
	 */
	public void updateGeoData(GeoData geoData) {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			session.update("SpotMapper.updateGeoData", geoData);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Returns the list of all GeoData instances from the database.
	 * 
	 * @return the list of all GeoData instances from the database.
	 */
	public List<GeoData> getAllGeoData() {

		SqlSession session = sqlSessionFactory.openSession();

		try {
			List<GeoData> list = session.selectList("SpotMapper.getAllGeoData");
			return list;
		} finally {
			session.close();
		}
	}

}
