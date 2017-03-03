package com.ssc.ssgm.fx.refdata.client;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.bcel.classfile.Constant;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.ssc.cloud.Client;
import com.ssc.cloud.Column;
import com.ssc.cloud.MetadataFetchException;
import com.ssc.cloud.ResultSet;
import com.ssc.cloud.ResultSetException;
import com.ssc.cloud.SignOnException;
import com.ssc.faw.util.GenException;
import com.ssc.faw.util.PwMatrixUtil;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.base.FXPassEncryptor;

/**
 * The implemetation client to call idf service.
 * 
 * @author e576929
 **/
public class CloudClientService extends AbstractClientService {

	private static Client client = null;
	/**
	 * Logger variable.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(CloudClientService.class);

	/**
	 * Reactor the constructor.
	 * 
	 * @param properties
	 *            is the cloud client properties.
	 */
	public CloudClientService() {
		super();
	}
	
    public CloudClientService(final Properties properties) {
        super(properties);
    }

	@Override
	public List<Map<String, Object>> callService(final String reuquestURL) {
		
		try {
			checkClientSession();
			LOGGER.info("start excute service url:" + reuquestURL);
			ResultSet rs = client.process(reuquestURL);
			LOGGER.info("end excute service url:" + reuquestURL);
			return getOutputForMap(rs);
		} catch (Exception e) {
			LOGGER.error("Executing Service Fails: " + e.getMessage(), e);
		} finally {
			try {
				if (client != null) {
					client.signOff();
				}
			} catch (Exception e) {
				LOGGER.error("Exception during sign-off:" + e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * Cloud Login.
	 * 
	 * @throws MalformedURLException
	 *             in case of error
	 * @throws SignOnException
	 *             in case of error
	 * @throws GenException
	 *             in case of error
	 * @return client.
	 */
	private Client cloudLogin() throws MalformedURLException, SignOnException,
			GenException {
		client = new Client(getURL());
		client.setPageSize(100000000);
		String password;
		if (useMatrix()) {
			PwMatrixUtil util = new PwMatrixUtil("FFO", getUserId());
			password = util.getPassword();
		} else {
//			password = getPassword();
			FXPassEncryptor passDecry = new FXPassEncryptor();
			password = passDecry.decrypt(getPassword(), Constants.SECURITYKEY);
		}
		client.signOn(getUserId(), password);
		return client;
	}

	public void checkClientSession() {
		try {
			client.process("__request=102");
		} catch (Exception e) {
			try {
				cloudLogin();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public List<Object[]> callService(final String reuquestURL,
			final List<String> headerList) {
		try {
		    checkClientSession();
			LOGGER.info("start excute service url:" + reuquestURL);
			ResultSet rs = client.process(reuquestURL);
			LOGGER.info("end excute service url:" + reuquestURL);
			return getOutputByColumn(rs, headerList);
		} catch (Exception e) {
			LOGGER.error("Executing Service Fails: " + e.getMessage(), e);
		} finally {
			try {
				if (client != null) {
					client.signOff();
				}
			} catch (Exception e) {
				LOGGER.error("Exception during sign-off:" + e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * Get ouputs from idf call, it return List<Object[]>.
	 * 
	 * @param rs
	 *            the result set.
	 * @return List<Object[]>
	 * @throws MetadataFetchException
	 *             in case of error
	 * @throws ResultSetException
	 *             in case of error
	 */
	public List<Object[]> getOutputToList(final ResultSet rs)
			throws MetadataFetchException, ResultSetException {
		Column[] columns = rs.getColumns();
		String[] outPuts;
		List<Object[]> result = new ArrayList<Object[]>();
		while (rs.next()) {
			outPuts = new String[columns.length];
			for (int j = 0; j < columns.length; j++) {
				outPuts[j] = rs.getString(columns[j].getName());
				LOGGER.debug(ArrayUtils.toString(outPuts));
			}
			result.add(outPuts);
		}
		return result;

	}

	/**
	 * Get ouputs from idf call, it return List<Object[]>.
	 * 
	 * @param rs
	 *            the result set.
	 * @param headerList
	 *            the headerList.
	 * @return List<Object[]>
	 * @throws MetadataFetchException
	 *             in case of error
	 * @throws ResultSetException
	 *             in case of error
	 */
	public List<Object[]> getOutputByColumn(final ResultSet rs,
			final List<String> headerList) throws MetadataFetchException,
			ResultSetException {
		if (headerList == null || headerList.isEmpty()) {
			return getOutputToList(rs);
		}
		String[] outPuts;
		List<Object[]> result = new ArrayList<Object[]>();
		while (rs.next()) {
			outPuts = new String[headerList.size()];
			for (int j = 0; j < headerList.size(); j++) {
				outPuts[j] = rs.getString(headerList.get(j));
			}
			LOGGER.debug(ArrayUtils.toString(outPuts));
			result.add(outPuts);
		}
		return result;

	}

	/**
	 * Get ouputs from idf call, it returnList<Map<String, Object>>.
	 * 
	 * @param rs
	 *            the result set.
	 * @return List<Map<String, Object>>
	 * @throws MetadataFetchException
	 *             in case of error
	 * @throws ResultSetException
	 *             in case of error
	 */
	public List<Map<String, Object>> getOutputForMap(final ResultSet rs)
			throws MetadataFetchException, ResultSetException {
		Column[] columns = rs.getColumns();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> rowResult = new HashMap<String, Object>();
		while (rs.next()) {
			rowResult = new HashMap<String, Object>();
			for (int j = 0; j < columns.length; j++) {
				rowResult.put(columns[j].getName(),
						rs.getString(columns[j].getName()));
			}
			result.add(rowResult);
		}
		return result;

	}
}
