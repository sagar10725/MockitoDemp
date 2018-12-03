import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith
import org.mockito.Mockito


import com.maximus.sdc.bizservices.common.data.UserContext;
import com.maximus.sdc.bizservices.eb.eligibility.MVXRecomputeData;
import com.maximus.sdc.bizservices.eb.eligibility.MVXRecomputeStatusData;
import com.maximus.sdc.bizservices.eb.tx.eligibility.TXEligibilityServiceImpl;
import com.maximus.sdc.bizservices.globalcontrols.GlobalControlService;
import com.maximus.sdc.core.common.valuelist.ValueListItem;
import com.maximus.sdc.core.common.valuelist.ValueListManager;
import com.maximus.sdc.eb.dm.model.EligSegmentAndDetails;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized)
public class TxEligServiceImplUnitTest {
	
	def loadEsds = {llistOfMaps->
		listOfMaps.collect{ map ->
			EligSegmentAndDetails esd = new EligSegmentAndDetails();
			map.each{
				if(it.key =="stc"  )
					esd.setSegmentTypeCd(it.value)
				else if(it.key== "sdv1")
					esd.setSegmentDetailValue1(it.value)
				else if(it.key== "sdv2")
					esd.setSegmentDetailValue2(it.value)
				else if(it.key== "sdv3")
					esd.setSegmentDetailValue3(it.value)
				else if(it.key=="sdv4")
					esd.setSegmentDetailValue4(it.value)
				else if(it.key=="sdv6")
					esd.setSegmentDetailValue6(it.value)
				else if(it.key=="sdv9")
					esd.setSegmentDetailValue9(it.value)
			}
			esd
		}
	}
	
	
//id,esds,mockFlag,planType,expElig,expSubProgram,expSubStat
	@Parameter
	public int id;
	@Parameter(1)
	public List<String> esds;
	@Parameter(2)
	public boolean mockFlag;
	@Parameter(3)
	public String planType;
	@Parameter(4)
	public String expElig;
	@Parameter(5)
	public String expSubProgram;
	@Parameter(6)
	public String expSubStat;
	
	
	@Test
		public void testEligStatusForMedicare(){
			UserContext uc = new UserContext();
			Long clientId =  1234
			final List<MVXRecomputeStatusData> statuses =  [
				new MVXRecomputeStatusData([mvxCode:"X", subStatus:null, reason:null, subProgram:null, planType:"STARKIDS"]),
				new MVXRecomputeStatusData([mvxCode:"X", subStatus:null, reason:null, subProgram:null, planType:"STARKIDS"])
			];
			MVXRecomputeData mvxRecomputeData = new MVXRecomputeData();
			mvxRecomputeData.setStatuses(statuses);

			TXEligibilityServiceImpl txEligibilityService = new TXEligibilityServiceImpl();
			List<EligSegmentAndDetails> esdList = loadEsds(esds);

			GlobalControlService globalControlServiceMock = Mockito.mock(GlobalControlService);
			ValueListManager valueListManagerMock = Mockito.mock(ValueListManager);
			ValueListItem valueListItemMock = Mockito.mock(ValueListItem);

			TXEligibilityServiceImpl txEligibilityServiceImplSpy =  Mockito.spy(TXEligibilityServiceImpl)

			Mockito.doReturn(true).when(txEligibilityServiceImplSpy).checkIfStarkidsClientIsAgeingOut(
					Mockito.any(com.maximus.sdc.bizservices.common.data.UserContext),
					Mockito.any(java.lang.Long),
					Mockito.any(java.util.Date)
					)
			Mockito.when(txEligibilityServiceImplSpy.checkIfStarkidsClientIsAgeingOut(
				Mockito.any(UserContext.class),
				Mockito.any(Long.class),
				Mockito.any(Date.class)
				)).thenReturn(true)
			Mockito.when(txEligibilityServiceImplSpy.getGlobalControlService()).thenReturn(globalControlServiceMock)
//			Mockito.when(txEligibilityServiceImplSpy.getValueListManager()).thenReturn(valueListManagerMock) ///commented since mockito can't mock final methods. 

			MVXRecomputeData mvxOutput = txEligibilityServiceImplSpy.computeMVX(uc,clientId,esdList,mvxRecomputeData)
			MVXRecomputeStatusData mvxResult = mvxOutput.getStatuses().findAll{it.getPlanType() == planType }.last()

			Assert.assertEquals(mvxResult.getMvxCode(),expElig)
			Assert.assertEquals(mvxResult.getSubProgram() , expSubProgram)
			Assert.assertEquals(mvxResult.getSubStatus() , expSubStat)
		}
		
		@Parameters
		public static List<Object[][]> data() {
			return [
// id , esds                                                                                           , mockFlag , planType   , expElig , expSubProgram  , expSubStat
//==============================================================================================================================================================================
[  1  , [[stc:'NOA',sdv2:'A']]                                                                         , true     , "MEDICAL"  , "P"     , "STAR"         , "TP40"              ],
[  2  , [[stc:'DEN-CAND',sdv4:'Y']]                                                                    , true     , 'DENTAL'   , 'M'     , 'DENTAL'       , 'MONTHLYPLANONLY'   ],
[  3  , [[stc:'DEN-CAND',sdv4:'V']]                                                                    , true     , 'DENTAL'   , 'V'     , 'DENTAL'       , 'NODA'              ],
[  4  , [[stc:'DEN-CAND',sdv4:'X']]                                                                    , true     , 'DENTAL'   , 'X'     , 'DENTAL'       , null                ],
[  5  , [[ stc:'MED-ELIG',sdv6:'STARPDUAL',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARPDUAL',sdv4:'Y']]   , true     , 'MEDICAL'  , 'M'     , 'STARP'        , 'DAILYPLANONLY'     ],
[  6  , [[ stc:'MEDICARE',sdv6:'STARKIDS',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARKIDS',sdv4:'Y']]     , true     , 'STARKIDS' , 'X'     , 'STARKIDSDUAL' , 'MONTHLYPLANONLY'   ],
[  7  , [[ stc:'MED-CAND',sdv6:'STARKIDS',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARKIDS',sdv4:'Y']]     , true     , 'STARKIDS' , 'X'     , 'STARKIDS'     , 'AA'                ],
[  8  , [[ stc:'MEDICARE',sdv6:'STARKIDS',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARPDUAL',sdv4:'V']]    , false    , 'MEDICAL'  , 'V'     , 'STARPDUAL'    , 'NODA'              ],
[  9  , [[ stc:'MED-CAND',sdv6:'STARKIDS',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARKIDS',sdv4:'Y']]     , true     , 'STARKIDS' , 'X'     , 'STARKIDS'     , 'AA'                ],
[  10 , [[ stc:'MEDICARE',sdv6:'STARKIDS',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARP',sdv4:'Y']]        , true     , 'MEDICAL'  , 'M'     , 'STARPDUAL'    , 'MONTHLYPLANONLY'   ],
[  11 , [[ stc:'MED-CAND',sdv6:'STARKIDS',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARP',sdv4:'Y']]        , true     , 'MEDICAL'  , 'M'     , 'STARP'        , 'AA'                ],
[  12 , [[ stc:'EXCLUDE',sdv6:'STARKIDS',sdv1:'Y'] ,[stc:'MED-CAND',sdv6:'STARKIDS',sdv4:'Y']]         , true     , 'STARKIDS' , 'X'     , 'STARKIDS'     , 'AA'                ],
[  13 , [[stc:'EXCLUDE',sdv2:'Y'],[stc:'DEN-CAND',sdv4:'X']]                                           , true     , 'DENTAL'   , 'X'     , 'DENTAL'       , null                ],
[  14 , [[ stc:'EXCLUDE',sdv6:'STARKIDS',sdv3:'Y'] ,[stc:'MED-CAND',sdv6:'STARKIDS',sdv4:'Y']]         , true     , 'STARKIDS' , 'X'     , null           , null                ],
[  15 , [[stc:'PEND',sdv2:'A']]                                                                        , true     , "MEDICAL"  , "P"     , "STAR"         , "TP40"              ],
[  16 , [[stc:'ME-INC',sdv2:'A']]                                                                      , true     , "MEDICAL"  , 'X'     , null           , null                ],
[  17 , [[ stc:'MED-CAND',sdv6:'STARKIDS',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARP',sdv4:'S']]        , true     , 'MEDICAL'  , 'X'     , 'STARP'        , 'AA'                ],
[  18 , [[ stc:'MEDICARE',sdv6:'STARKIDS',sdv9:'TP40'] ,[stc:'MED-CAND',sdv6:'STARKIDSDUAL',sdv4:'V']] , false    , 'STARKIDS' , 'V'     , 'STARKIDSDUAL' , 'NODA'              ]
		] as Object[][];
	}
}
