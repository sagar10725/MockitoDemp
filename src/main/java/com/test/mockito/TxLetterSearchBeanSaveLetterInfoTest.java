package com.maximus.sdc.webapp.eb.tx.letterTest;

import java.util.Date;

import javax.faces.event.ActionEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.maximus.sdc.bizservices.common.data.UserContext;
import com.maximus.sdc.bizservices.letter.LetterRequestManager;
import com.maximus.sdc.core.common.util.DateUtil;
import com.maximus.sdc.core.webapp.util.WebUserContextHelper;
import com.maximus.sdc.eb.dm.model.LetterRequest;
import com.maximus.sdc.webapp.eb.letter.LetterBean;
import com.maximus.sdc.webapp.eb.letter.LetterOutDataBean;
import com.maximus.sdc.webapp.eb.letter.LetterRequestDataDetailsDataModel;
import com.maximus.sdc.webapp.eb.letter.LetterStatusHistoryDataModel;
import com.maximus.sdc.webapp.eb.tx.letter.TxLetterSearchBean;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ WebUserContextHelper.class, DateUtil.class })
public class TxLetterSearchBeanSaveLetterInfoTest {

	@Before
	public void setUp() {
		PowerMockito.mockStatic(WebUserContextHelper.class);
		PowerMockito.mockStatic(DateUtil.class);
	}

	@Test
	public void givenNewLeterStatesNullThenExpectNoSaveLetter() {

		String newLetterStatus = null;
		ActionEvent ae = Mockito.mock(ActionEvent.class);

		LetterRequestManager letterRequestManager = Mockito.mock(LetterRequestManager.class);
		LetterRequest letterRequest = Mockito.mock(LetterRequest.class);
		UserContext userContext = Mockito.mock(UserContext.class);
		LetterBean letterBean = Mockito.mock(LetterBean.class);		
		
		LetterStatusHistoryDataModel letterStatusHistoryDataModel = Mockito.mock(LetterStatusHistoryDataModel.class);
		LetterRequestDataDetailsDataModel letterRequestDataDetailsDataModel = Mockito.mock(LetterRequestDataDetailsDataModel.class);
		LetterOutDataBean letterOutDataBean = Mockito.mock(LetterOutDataBean.class);
		
		Mockito.when(WebUserContextHelper.getUserContext()).thenReturn(userContext);
		Mockito.when(letterBean.getSelectedLetterRequest()).thenReturn(letterRequest);
		Mockito.doNothing().when(letterRequestManager).updateLetterRequest(userContext, letterRequest);
		
		Mockito.doNothing().when(letterStatusHistoryDataModel).reset();
		Mockito.doNothing().when(letterRequestDataDetailsDataModel).reset();
		Mockito.doNothing().when(letterOutDataBean).reset();
		Mockito.when(letterBean.getNewLetterStatus()).thenReturn(null);		
		Mockito.when(letterRequest.getReturnReasonCd()).thenReturn(null);
		
		TxLetterSearchBean classUnderTest = new TxLetterSearchBean();

		classUnderTest.setLetterRequestManager(letterRequestManager);
		classUnderTest.setNewLetterStatus(newLetterStatus);
		classUnderTest.setSelectedLetterRequest(letterRequest);
		
		classUnderTest.setLetterStatusHistoryDataModel(letterStatusHistoryDataModel);
		classUnderTest.setLetterRequestDataDetailsDataModel(letterRequestDataDetailsDataModel);
		classUnderTest.setLetterOutDataBean(letterOutDataBean);
		classUnderTest.setSelectedLetterRequest(letterRequest);
		classUnderTest.saveLetterInfo(ae);

	}
	
	
	@Test
	public void givenNewLeterStatesAndDateNotNullThenExpectSaveLetter() {

		String newLetterStatus = new String("StatusCd");
		String recordCd = new String("recordCd");
		ActionEvent ae = Mockito.mock(ActionEvent.class);

		LetterRequestManager letterRequestManager = Mockito.mock(LetterRequestManager.class);
		LetterRequest letterRequest = Mockito.mock(LetterRequest.class);
		UserContext userContext = Mockito.mock(UserContext.class);
		LetterBean letterBean = Mockito.mock(LetterBean.class);
		
		Date currentDate = new Date();
		LetterStatusHistoryDataModel letterStatusHistoryDataModel = Mockito.mock(LetterStatusHistoryDataModel.class);
		LetterRequestDataDetailsDataModel letterRequestDataDetailsDataModel = Mockito.mock(LetterRequestDataDetailsDataModel.class);
		LetterOutDataBean letterOutDataBean = Mockito.mock(LetterOutDataBean.class);
		
		Mockito.when(letterBean.getNewLetterStatus()).thenReturn(newLetterStatus);
		Mockito.doNothing().when(letterRequest).setStatusCd(newLetterStatus);
		
		Mockito.when(WebUserContextHelper.getUserContext()).thenReturn(userContext);
		Mockito.when(letterBean.getSelectedLetterRequest()).thenReturn(letterRequest);
		Mockito.doNothing().when(letterRequestManager).updateLetterRequest(userContext, letterRequest);		
		
		Mockito.doNothing().when(letterStatusHistoryDataModel).reset();
		Mockito.doNothing().when(letterRequestDataDetailsDataModel).reset();
		Mockito.doNothing().when(letterOutDataBean).reset();
		
		Mockito.when(letterRequest.getReturnReasonCd()).thenReturn(recordCd);
		Mockito.when(letterRequest.getReturnDate()).thenReturn(currentDate);

		TxLetterSearchBean classUnderTest = new TxLetterSearchBean();

		classUnderTest.setLetterRequestManager(letterRequestManager);
		classUnderTest.setNewLetterStatus(newLetterStatus);
		classUnderTest.setSelectedLetterRequest(letterRequest);
		
		classUnderTest.setLetterStatusHistoryDataModel(letterStatusHistoryDataModel);
		classUnderTest.setLetterRequestDataDetailsDataModel(letterRequestDataDetailsDataModel);
		classUnderTest.setLetterOutDataBean(letterOutDataBean);
		classUnderTest.setSelectedLetterRequest(letterRequest);
		classUnderTest.saveLetterInfo(ae);

	}
	
	
	
	@Test
	public void givenNewLeterStatesNotNullThenExpectSaveLetter() {

		String newLetterStatus = new String("StatusCd");
		String recordCd = new String("recordCd");
		ActionEvent ae = Mockito.mock(ActionEvent.class);

		LetterRequestManager letterRequestManager = Mockito.mock(LetterRequestManager.class);
		LetterRequest letterRequest = Mockito.mock(LetterRequest.class);
		UserContext userContext = Mockito.mock(UserContext.class);
		LetterBean letterBean = Mockito.mock(LetterBean.class);
		
		Date currentDate = new Date();
		LetterStatusHistoryDataModel letterStatusHistoryDataModel = Mockito.mock(LetterStatusHistoryDataModel.class);
		LetterRequestDataDetailsDataModel letterRequestDataDetailsDataModel = Mockito.mock(LetterRequestDataDetailsDataModel.class);
		LetterOutDataBean letterOutDataBean = Mockito.mock(LetterOutDataBean.class);
		
		Mockito.when(letterBean.getNewLetterStatus()).thenReturn(newLetterStatus);
		Mockito.doNothing().when(letterRequest).setStatusCd(newLetterStatus);
		
		Mockito.when(WebUserContextHelper.getUserContext()).thenReturn(userContext);
		Mockito.when(letterBean.getSelectedLetterRequest()).thenReturn(letterRequest);
		Mockito.doNothing().when(letterRequestManager).updateLetterRequest(userContext, letterRequest);		
		
		Mockito.doNothing().when(letterStatusHistoryDataModel).reset();
		Mockito.doNothing().when(letterRequestDataDetailsDataModel).reset();
		Mockito.doNothing().when(letterOutDataBean).reset();
		
		Mockito.when(letterRequest.getReturnReasonCd()).thenReturn(recordCd);
		Mockito.when(letterRequest.getReturnDate()).thenReturn(null);
		Mockito.doNothing().when(letterRequest).setReturnDate(currentDate);

		TxLetterSearchBean classUnderTest = new TxLetterSearchBean();

		classUnderTest.setLetterRequestManager(letterRequestManager);
		classUnderTest.setNewLetterStatus(newLetterStatus);
		classUnderTest.setSelectedLetterRequest(letterRequest);		
		classUnderTest.setLetterStatusHistoryDataModel(letterStatusHistoryDataModel);
		classUnderTest.setLetterRequestDataDetailsDataModel(letterRequestDataDetailsDataModel);
		classUnderTest.setLetterOutDataBean(letterOutDataBean);
		classUnderTest.setSelectedLetterRequest(letterRequest);
		classUnderTest.saveLetterInfo(ae);

	}
}
