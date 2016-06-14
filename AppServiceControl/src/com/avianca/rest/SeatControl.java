package com.avianca.rest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.context.MessageContext;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;

import com.amadeus.entity.Passenger;
import com.amadeus.xml.apalrr_13_2_1a.DCSLSTGetPassengerListReplyDocument.DCSLSTGetPassengerListReply.FlightDetailsGroup.CustomerLevel;
import com.amadeus.xml.vlsslq_06_1_1a.OriginatorIdentificationDetailsTypeI;
import com.amadeus.xml.vlsslq_06_1_1a.SecurityAuthenticateDocument.SecurityAuthenticate;
import com.amadeus.xml.vlsslq_06_1_1a.UserIdentificationType;
import com.amadeus.xml.vlsslr_06_1_1a.SecurityAuthenticateReplyDocument;


@Path("avianca/seatcontrol")
public class SeatControl {
	@GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("teste")
    public String listaPessoa() {  
         
        return "bla,bla,bla,bla";
    }  
	
	@GET
	@Path("passengerlist/{sop}/{nv}/{dv}/{so}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getListPassager(@PathParam("sop")String sop,@PathParam("nv")String nv,@PathParam("dv")String dv,@PathParam("so")String so) {	
		List<Passenger> passengers= new ArrayList<>();
		JSONArray list = new JSONArray();
		try {			
			//http://localhost:8080/AppServiceControl/rest/avianca/seatcontrol/passengerlist/O6/6318/20160525/GRU
			System.out.println(sop+nv+dv+so);
			/*passengers = getPassengerList("06","6318","20160525","GRU");*/
			//passengers = getPassengerList(sop,nv,dv,so);	
			Passenger[][] seats = organizeSeats(getPassengerList(sop,nv,dv,so));
			
			final int len = seats[0].length;
            for (int i = 0; i < len; i++) {
            	passengers.add(seats[0][i]);
            	passengers.add(seats[1][i]);
            	passengers.add(seats[2][i]);
            	passengers.add(seats[3][i]);
            	passengers.add(seats[4][i]);
            	passengers.add(seats[5][i]);
            	
            }
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.ok().entity(e.getMessage()).build();
		}
		return Response.ok().entity(new GenericEntity<List<Passenger>>(passengers) {}).build();

	}
	
	@GET
	@Path("passengerlistarray/{sop}/{nv}/{dv}/{so}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getListPassagerObj(@PathParam("sop")String sop,@PathParam("nv")String nv,@PathParam("dv")String dv,@PathParam("so")String so) {	
		
		try {
			return organizeSeats(getPassengerList(sop,nv,dv,so));
		} catch (Exception e) {
			/*return Response.ok().entity(e.getMessage()).build();*/
			return e.getMessage();
		}

	}
	
	
	
	 public List<Passenger> getPassengerList(String _marketingCarrier, String _flightNumber, String _departureDate, String _boardPoint) throws java.lang.Exception {
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
	        CustomerLevel[] customers = flightDetailsGrp.getCustomerLevelArray();

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
	 
	 
	 private Passenger[][] organizeSeats(final List<Passenger> _passengers) {
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


}
