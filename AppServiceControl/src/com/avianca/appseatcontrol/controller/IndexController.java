package com.avianca.appseatcontrol.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.context.MessageContext;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.amadeus.entity.Passenger;
import com.amadeus.xml.AmadeusWSTest;
import com.amadeus.xml.apalrr_13_2_1a.DCSLSTGetPassengerListReplyDocument.DCSLSTGetPassengerListReply.FlightDetailsGroup.CustomerLevel;
import com.amadeus.xml.vlsslq_06_1_1a.OriginatorIdentificationDetailsTypeI;
import com.amadeus.xml.vlsslq_06_1_1a.UserIdentificationType;
import com.amadeus.xml.vlsslq_06_1_1a.SecurityAuthenticateDocument.SecurityAuthenticate;
import com.amadeus.xml.vlsslr_06_1_1a.SecurityAuthenticateReplyDocument;
import com.google.gson.Gson;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/IndexController")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//AmadeusWSTest test = new AmadeusWSTest();
		String siglaOp  = request.getParameter("siglaOp");
		String numVoo  = request.getParameter("numVoo");
		String dtaVoo  = request.getParameter("dtaVoo");
		String siglaOrigin  = request.getParameter("siglaOrigin");
		PrintWriter out =null;
		response.setContentType("text/html");
		response.setBufferSize(1024);
		Passenger[][] seats=null;
	//	 try {  
    
      
   /* response.setCharacterEncoding("UTF-8");  
    response.setContentType("application/json");*/   
    out = response.getWriter();   
    //List<Passenger> passengers = getPassengerList("06","6318","20160525","GRU");
    List<Passenger> passengers = null;
	
		try {
			passengers = getPassengerList(siglaOp,numVoo,dtaVoo,siglaOrigin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    
    out.println("<HTML>");
    // Start on the body
    out.println("<BODY>");
    out.println("<CENTER>");
    out.println("<table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=90% >");   
    
    out.print("<tr><th>N°Seat</th><th>A</th><th>B</th><th>C</th><th>D</th><th>E</th><th>K</th></tr>");
    int count=0;
    
    		if (passengers != null && passengers.size() > 0) {
    	         seats = organizeSeats(passengers);
    		
    	        final int len = seats[0].length;
    	        for (int i = 0; i < len; i++) {
    	        	count++;
    	            // passageiros em todas as filas
    	            Passenger passA = seats[0][i];    	            
    	            Passenger passB = seats[1][i];    	           
    	            Passenger passC = seats[2][i];    	            
    	            Passenger passD = seats[3][i];    	            
    	            Passenger passE = seats[4][i];    	           
    	            Passenger passK = seats[5][i]; 
    	            
    	            out.print(               "<tr><td>"+count+"</td>"
   		                   + "<td> "+strFormat(passA == null ? "" : passA.getName(),50)+"</td>"
   		                   + "<td> "+strFormat(passB == null ? "" : passB.getName(),50)+"</td>"
   				           + "<td> "+strFormat(passC == null ? "" : passC.getName(),50)+"</td>"
   						   + "<td> "+strFormat(passD == null ? "" : passD.getName(),50)+"</td>"
   						   + "<td> "+strFormat(passE == null ? "" : passE.getName(),50)+"</td>"
   						   + "<td> "+strFormat(passK == null ? "" : passK.getName(),50)+"</td></tr>");

    	           
    	            
    	              /*out.print(               "<tr><td>"+count+"</td>"
    	              		                   + "<td>Nome : "+strFormat(passA == null ? "" : passA.getName()+" \nType " + passA.getType() + " FqtvNumber " + passA.getFqtvNumber() + " SsrDetails " + passA.getSsrDetails(),50)+"</td>"
    	              		                   + "<td>Nome : "+strFormat(passB == null ? "" : passB.getName()+" \nType " + passB.getType() + " FqtvNumber " + passB.getFqtvNumber() + " SsrDetails " + passB.getSsrDetails(),50)+"</td>"
    	              				           + "<td>Nome : "+strFormat(passC == null ? "" : passC.getName()+" \nType " + passC.getType() + " FqtvNumber " + passC.getFqtvNumber() + " SsrDetails " + passC.getSsrDetails(),50)+"</td>"
    	              						   + "<td>Nome : "+strFormat(passD == null ? "" : passD.getName()+" \nType " + passD.getType() + " FqtvNumber " + passD.getFqtvNumber() + " SsrDetails " + passD.getSsrDetails(),50)+"</td>"
    	              						   + "<td>Nome : "+strFormat(passE == null ? "" : passE.getName()+" \nType " + passE.getType() + " FqtvNumber " + passE.getFqtvNumber() + " SsrDetails " + passE.getSsrDetails(),50)+"</td>"
    	              						   + "<td>Nome : "+strFormat(passK == null ? "" : passK.getName()+" \nType " + passK.getType() + " FqtvNumber " + passK.getFqtvNumber() + " SsrDetails " + passK.getSsrDetails(),50)+"</td></tr>");
*/    	             // out.print("<tr><td>"+1+"</td><td>fdafa</td><td>fadfdafdsafds</td><td>fadfadsfdsafdsa</td><td>fadsfdfdsaf</td><td>fdafdfdsaf</td><td>fdfadfafdsfdasfdaf</td></tr>");
    		    }
       		}    		
	
    		out.println("</table>");
            
            
         /*  bloco para teste */           
           /* final int len = seats[0].length;
            for (int i = 0; i < len; i++) {
                // passageiros em todas as filas
                final Passenger passA = seats[0][i], passB = seats[1][i], passC = seats[2][i], passD = seats[3][i], 
                		passE = seats[4][i], passK = seats[5][i];

                // printa acento:
                out.print(strFormat((i + 1) + "", 8));

                // printa fileiras
                out.print(strFormat((passA == null ? "" : passA.getName() + " | " + passA.getType() + " | " + passA.getFqtvNumber() + " | " + passA.getSsrDetails()), 10));
                out.print(strFormat((passB == null ? "" : passB.getName() + " | " + passB.getType() + " | " + passB.getFqtvNumber() + " | " + passB.getSsrDetails()), 10));
                out.print(strFormat((passC == null ? "" : passC.getName() + " | " + passC.getType() + " | " + passC.getFqtvNumber() + " | " + passC.getSsrDetails()), 10));
                out.print(strFormat((passD == null ? "" : passD.getName() + " | " + passD.getType() + " | " + passD.getFqtvNumber() + " | " + passD.getSsrDetails()), 10));
                out.print(strFormat((passE == null ? "" : passE.getName() + " | " + passE.getType() + " | " + passE.getFqtvNumber() + " | " + passE.getSsrDetails()), 10));
                out.print(strFormat((passK == null ? "" : passK.getName() + " | " + passK.getType() + " | " + passK.getFqtvNumber() + " | " + passK.getSsrDetails()), 10));
                out.print("\n");
            }*/
    
    		out.println("</CENTER>");
            out.println("</BODY></HTML>");	 
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	


    /**
     * Efetua a autenticacao no Amadeus-WS e busca a lista de passageiros.
     *
     * @param _marketingCarrier
     * @param _flightNumber
     * @param _departureDate
     * @param _boardPoint
     * 
     * @return
     * 
     * @throws java.lang.Exception
     */
    public List<Passenger> getPassengerList(String _marketingCarrier, String _flightNumber, String _departureDate, String _boardPoint) throws java.lang.Exception {
    	CustomerLevel[] customers = null;
    	
    	com.amadeus.xml.AmadeusWebServicesStub stub = new com.amadeus.xml.AmadeusWebServicesStub(); //the default implementation should point to the right endpoint

        com.amadeus.xml.vlsslq_06_1_1a.SecurityAuthenticateDocument security_Authenticate64 = (com.amadeus.xml.vlsslq_06_1_1a.SecurityAuthenticateDocument) getTestObject(com.amadeus.xml.vlsslq_06_1_1a.SecurityAuthenticateDocument.class);
        // TODO : Fill in the security_Authenticate64 here
        SecurityAuthenticate auth = security_Authenticate64.addNewSecurityAuthenticate();

        UserIdentificationType userIdentifier = auth.addNewUserIdentifier();
        userIdentifier.setOriginatorTypeCode("U");
        userIdentifier.setOriginator("BIMIDWL");

        OriginatorIdentificationDetailsTypeI originIdentification = userIdentifier.addNewOriginIdentification();
        originIdentification.setSourceOffice("SAOO600DC");

        com.amadeus.xml.vlsslq_06_1_1a.ReferenceInformationTypeI dutyCode = auth.addNewDutyCode();
        com.amadeus.xml.vlsslq_06_1_1a.ReferencingDetailsTypeI dutyCodeDetails = dutyCode.addNewDutyCodeDetails();
        dutyCodeDetails.setReferenceQualifier("DUT");
        dutyCodeDetails.setReferenceIdentifier("SU");

        com.amadeus.xml.vlsslq_06_1_1a.SystemDetailsInfoType systemDetails = auth.addNewSystemDetails();
        com.amadeus.xml.vlsslq_06_1_1a.SystemDetailsTypeI organizationDetails = systemDetails.addNewOrganizationDetails();
        organizationDetails.setOrganizationId("O6");

        com.amadeus.xml.vlsslq_06_1_1a.BinaryDataType passwordInfo = auth.addNewPasswordInfo();
        passwordInfo.setDataLength(new BigInteger("16"));
        passwordInfo.setDataType("E");
        passwordInfo.setBinaryData("QWVyb1BvcnRhbDIwMTQwNQ==");

        com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument session65 = (com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument) getTestObject(com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument.class);
        // TODO : Fill in the session65 here
        com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument.Session session = session65.addNewSession();
        session.setSessionId("");
        session.setSequenceNumber("");
        session.setSecurityToken("");

        SecurityAuthenticateReplyDocument resp = stub.security_Authenticate(security_Authenticate64, session65);
        //        System.out.println("\n BODY LOGIN: \n" + resp.toString());

        MessageContext message = stub._getServiceClient().getLastOperationContext().getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
        SOAPHeader soapHeader = message.getEnvelope().getHeader();
        OMElement sessionHeader = soapHeader.getFirstElement();

        Iterator childs = sessionHeader.getChildElements();

        String SessionId = null;
        String SequenceNumber = null;
        String SecurityToken = null;

        for (int i = 0; childs.hasNext(); i++) {
            OMElement ome = (OMElement) childs.next();
            switch (i) {
                case 0:
                    SessionId = ome.getText();
                    break;
                case 1:
                    SequenceNumber = ome.getText();
                    break;
                case 2:
                    SecurityToken = ome.getText();
                    break;
            }
        }

        //--- get-passenger-list ----------------------------------------------

        com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument dCSLST_GetPassengerList58 = (com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument) getTestObject(com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument.class);
        // TODO : Fill in the dCSLST_GetPassengerList58 here
        com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument.DCSLSTGetPassengerList body = dCSLST_GetPassengerList58.addNewDCSLSTGetPassengerList();
        com.amadeus.xml.apalrq_13_2_1a.FlightDetailsResponseType flightInfo = body.addNewFlightInfo();
        com.amadeus.xml.apalrq_13_2_1a.OutboundCarrierDetailsTypeI carrierDetails = flightInfo.addNewCarrierDetails();
        carrierDetails.setMarketingCarrier(_marketingCarrier);

        com.amadeus.xml.apalrq_13_2_1a.OutboundFlightNumberDetailstypeI flightDetails = flightInfo.addNewFlightDetails();
        flightDetails.setFlightNumber(new BigInteger(_flightNumber));

        flightInfo.setDepartureDate(_departureDate);
        flightInfo.setBoardPoint(_boardPoint);

        com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument.DCSLSTGetPassengerList.OrFiltersQuery orFiltersQuery = body.addNewOrFiltersQuery();
        orFiltersQuery.addNewDummy();

        com.amadeus.xml.apalrq_13_2_1a.DisplayPassengerListRequestAndFiltersQuery andFiltersQuery = orFiltersQuery.addNewAndFiltersQuery();
        com.amadeus.xml.apalrq_13_2_1a.CodedAttributeType passengerListFilterName = andFiltersQuery.addNewPassengerListFilterName();
        com.amadeus.xml.apalrq_13_2_1a.CodedAttributeInformationType attributeDetails = passengerListFilterName.addNewAttributeDetails();
        attributeDetails.setAttributeType("SS");
        attributeDetails.setAttributeDescription("CS");

        com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument session59 = (com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument) getTestObject(com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument.class);
        // TODO : Fill in the session59 here
        session = session59.addNewSession();
        session.setSessionId(SessionId);
        session.setSequenceNumber(SequenceNumber);
        session.setSecurityToken(SecurityToken);

        com.amadeus.xml.apalrr_13_2_1a.DCSLSTGetPassengerListReplyDocument resplyDoc = stub.dCSLST_GetPassengerList(dCSLST_GetPassengerList58, session59);
        System.out.println("\n\n BODY GET-PASSENGER-LIST: \n" + resplyDoc.toString());

        // navega pela estrutura de resposta para obter a relacao de passageiros.
        com.amadeus.xml.apalrr_13_2_1a.DCSLSTGetPassengerListReplyDocument.DCSLSTGetPassengerListReply reply = resplyDoc.getDCSLSTGetPassengerListReply();
        com.amadeus.xml.apalrr_13_2_1a.DCSLSTGetPassengerListReplyDocument.DCSLSTGetPassengerListReply.FlightDetailsGroup flightDetailsGrp = reply.getFlightDetailsGroup();
        
        try{
        
        customers = flightDetailsGrp.getCustomerLevelArray();
        }catch(Exception e){
        	throw e;
        }

        // se nao ha passageiros, entao retorna nulo.
        if (customers == null || customers.length < 1)
            return null;

        // identifica o comprimento do array, para otimizar a criacao do array-list.
        int i = (customers == null) ? 0 : customers.length;
        List<Passenger> passengers = new ArrayList<Passenger>(i);

        // navega pela estrutura xml da resposta, para obter os dados dos passageiros.
        for (CustomerLevel customerLevel : customers) {
            Passenger passenger = new Passenger();

            passenger.setSurname(customerLevel.getCustomerData().getPaxDetails().getSurname());
            passenger.setGivenName(customerLevel.getCustomerData().getOtherPaxDetails().getGivenName());
            passenger.setSeat(customerLevel.getSeatInformation().getSeatNumberInfo().getSeatDetails().getSeatNumber());

            passengers.add(passenger);
        }

        Collections.sort(passengers);
       

        return passengers;
    }

    /**
     * Distribui os assentos em filas, retornando uma matriz com os passageiros nas posicoes do vetor da respectiva fila.
     *
     * @param _passengers
     * @return
     */
    public Passenger[][] organizeSeats(final List<Passenger> _passengers) {
        if (_passengers == null || _passengers.size() < 1)
            return null;

        // identifica as filas, e o maior numero de acento.
        int maxInt = 0;
        int lastChr = 0;
        for (Passenger passenger : _passengers) {
            final int number = passenger.getSeatNumber();
            final char line = passenger.getSeatLine();

            if (number > maxInt)
                maxInt = number;
            if (line > lastChr)
                lastChr = line;
        }

        // filas A,B,C,D,E,K = 6
        final Passenger[][] seats = new Passenger[6][maxInt];

        // repassa os passageiros para a estrutura de acentos:
        for (Passenger passenger : _passengers) {
            // identifica o numero da fila:   A=0, B=1, C=2, D=3, E=4, K=5
            int fila = ((int) passenger.getSeatLine().charValue()) - 65; // 65 = A
            if (fila == 10)
                fila = 5;
            int index = passenger.getSeatNumber() - 1;

            seats[fila][index] = passenger;
        }

        return seats;
    }

    /**
     * Auto generated test method
     */
    //    public void testdCSLST_GetPassengerList() throws java.lang.Exception {
    //        com.amadeus.xml.AmadeusWebServicesStub stub = new com.amadeus.xml.AmadeusWebServicesStub(); //the default implementation should point to the right endpoint
    //
    //        com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument dCSLST_GetPassengerList58 = (com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument) getTestObject(com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument.class);
    //        // TODO : Fill in the dCSLST_GetPassengerList58 here
    //        com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument.DCSLSTGetPassengerList body = dCSLST_GetPassengerList58.addNewDCSLSTGetPassengerList();
    //        com.amadeus.xml.apalrq_13_2_1a.FlightDetailsResponseType flightInfo = body.addNewFlightInfo();
    //        com.amadeus.xml.apalrq_13_2_1a.OutboundCarrierDetailsTypeI carrierDetails = flightInfo.addNewCarrierDetails();
    //        carrierDetails.setMarketingCarrier("O6");
    //
    //        com.amadeus.xml.apalrq_13_2_1a.OutboundFlightNumberDetailstypeI flightDetails = flightInfo.addNewFlightDetails();
    //        flightDetails.setFlightNumber(new BigInteger("6318"));
    //
    //        flightInfo.setDepartureDate("20160525");
    //        flightInfo.setBoardPoint("GRU");
    //
    //        com.amadeus.xml.apalrq_13_2_1a.DCSLSTGetPassengerListDocument.DCSLSTGetPassengerList.OrFiltersQuery orFiltersQuery = body.addNewOrFiltersQuery();
    //        orFiltersQuery.addNewDummy();
    //        
    //        com.amadeus.xml.apalrq_13_2_1a.DisplayPassengerListRequestAndFiltersQuery andFiltersQuery = orFiltersQuery.addNewAndFiltersQuery();
    //        com.amadeus.xml.apalrq_13_2_1a.CodedAttributeType passengerListFilterName = andFiltersQuery.addNewPassengerListFilterName();
    //        com.amadeus.xml.apalrq_13_2_1a.CodedAttributeInformationType attributeDetails = passengerListFilterName.addNewAttributeDetails();
    //        attributeDetails.setAttributeType("SS");
    //        attributeDetails.setAttributeDescription("CS"); 
    //        
    //        
    //        com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument session59 = (com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument) getTestObject(com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument.class);
    //        // TODO : Fill in the session59 here
    //        com.amadeus.xml.ws._2009._01.wbs_session_2_0_xsd.SessionDocument.Session session = session59.addNewSession();
    //        session.setSessionId("00BBBVSPVW");
    //        session.setSequenceNumber("1");
    //        session.setSecurityToken("CQ1JDYAN2XLW36031NM17OUMS");
    //        
    //        com.amadeus.xml.apalrr_13_2_1a.DCSLSTGetPassengerListReplyDocument resp = stub.dCSLST_GetPassengerList(dCSLST_GetPassengerList58, session59);
    //        System.out.println(resp);
    //        
    //        assertNotNull(resp);
    //    }

    //--- HELPERS -------------------------------------------------------------

    //Create the desired XmlObject and provide it as the test object
    private org.apache.xmlbeans.XmlObject getTestObject(java.lang.Class<?> type) throws java.lang.Exception {
        java.lang.reflect.Method creatorMethod = null;

        if (org.apache.xmlbeans.XmlObject.class.isAssignableFrom(type)) {
            Class<?>[] declaredClasses = type.getDeclaredClasses();

            for (int i = 0; i < declaredClasses.length; i++) {
                Class<?> declaredClass = declaredClasses[i];

                if (declaredClass.getName().endsWith("$Factory")) {
                    creatorMethod = declaredClass.getMethod("newInstance", null);

                    break;
                }
            }
        }

        if (creatorMethod != null) {
            return (org.apache.xmlbeans.XmlObject) creatorMethod.invoke(null, null);
        } else {
            throw new java.lang.Exception("Creator not found!");
        }
    }

    /**
     * Metodo repeat - repete uma string por um determinado numero de vezes.
     *
     * @param _str
     * @param _len
     * @return
     */
    private static String repeat(String _str, int _len) {
        if (_str == null || _str.length() == 0 || _len < 1)
            return "";

        StringBuilder text = new StringBuilder();
        for (int i = 0; i < _len; i++)
            text.append(_str);

        return text.toString();
    }

    /**
     * Metodo strLen - formata uma string em determinado tamanho fixo, alinhando a esquerda.
     *
     * @param _str
     * @param _len
     * @return
     */
    private static String strLen(String _str, int _len) {
        if (_len < 1)
            return "";

        if (_str == null || _str.length() == 0)
            _str = " ";

        StringBuilder text = new StringBuilder(_str).append(repeat(" ", _len));
        return text.toString().substring(0, _len);
    }

    //--- MAIN -------------------------------------------------------------

    public static void main(String[] args) {
        final AmadeusWSTest amadeusWS = new AmadeusWSTest();
        
        try {
            // busca os passageiros do voo:
            String marketingCarrier, flightNumber, departureDate, boardPoint;

            List<Passenger> passengers = amadeusWS.getPassengerList("O6", // marketingCarrier
                                                                              "6318", // flightNumber
                                                                              "20160525", // departureDate
                                                                              "GRU"); // boardPoint
            //            System.out.println("RESPONSE: \n" + passengers);

            // organiza os passageiros em filas de acentos:
            if (passengers != null && passengers.size() > 0) {
                Passenger[][] seats = amadeusWS.organizeSeats(passengers);

                // printa filas
                System.out.println("SEAT    A                     B                     C                     D                     E                     K");
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");

                final int len = seats[0].length;
                for (int i = 0; i < len; i++) {
                    // passageiros em todas as filas
                    final Passenger passA = seats[0][i], passB = seats[1][i], passC = seats[2][i], passD = seats[3][i], passE = seats[4][i], passK = seats[5][i];

                    // printa acento:
                    System.out.print(strLen((i + 1) + "", 8));

                    // printa filas
                    System.out.print(strLen((passA == null ? "X" : passA.getName()), 22));
                    System.out.print(strLen((passB == null ? "X" : passB.getName()), 22));
                    System.out.print(strLen((passC == null ? "X" : passC.getName()), 22));
                    System.out.print(strLen((passD == null ? "X" : passD.getName()), 22));
                    System.out.print(strLen((passE == null ? "X" : passE.getName()), 22));
                    System.out.print(strLen((passK == null ? "X" : passK.getName()), 22));
                    System.out.println("\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String strFormat(String _str, int _len) {
        if (_len < 1)
            return "";

        if (_str == null || _str.length() == 0)
            _str = " ";

        StringBuilder text = new StringBuilder(_str).append(repeat(" ", _len));
        return text.toString().substring(0, _len);
    }

}
