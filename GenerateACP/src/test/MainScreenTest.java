package test;

import main.*;
import orbital.logic.imp.Formula;
import orbital.logic.imp.Logic;
import orbital.logic.sign.ParseException;
import orbital.moon.logic.ClassicalLogic;
import junit.framework.TestCase;
import junit.framework.Test;
import static junit.framework.Assert.assertEquals;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;

public class MainScreenTest extends TestCase {
	protected Logic logic = new ClassicalLogic();
	public void testElimination() throws Exception {
		List<Expression> listCond = new ArrayList<Expression>();
		listCond.add(new Expression("Contract", "CarAge", "EqualOrBiggerThan", null, null, "2"));
		listCond.add(new Expression("Car", "Level", "Equal", null, null, "Luxury"));
		listCond.add(new Expression("Contract", "CarMileage", "SmallerThan", null, null, "30000"));
		List<Expression> listCons = new ArrayList<Expression>();
		listCons.add(new Expression("Contract", "PreRentCondition", "Equal", null, null, "SoundCondition"));
		int[] label = { 0, 1, 4, 5, 6 };
		Artifact artContract = new Artifact("Contract");
		Artifact artCar = new Artifact("Car");
		Artifact artCustomer = new Artifact("Customer");
		List<BusinessTask> taskList = new ArrayList<BusinessTask>();
		BusinessTask[] tList = new BusinessTask[7];
		tList[0] = new BusinessTask();
		tList[0].name = "Reserve";

		tList[0].addLiteral2Pre("CarIsNonBooked");
		tList[0].addLiteral2Pre("ContractIsInitial");
		tList[0].addLiteral2Pre("~ContractAtCarID");
		tList[0].addLiteral2Pre("~ContractAtContractID");
		// tList[1].preCondition = tList[1].getFormula4Pre(logic);
		tList[0].setPreCondition((Formula) logic
				.createExpression("CarIsNonBooked & ContractIsInitial & (~ContractAtCarID) & ~ContractAtContractID"));

		tList[0].addLiteral2Post("CarIsBooked");
		tList[0].addLiteral2Post("ContractIsOpened");
		tList[0].addLiteral2Post("ContractAtCarID");
		tList[0].addLiteral2Post("ContractAtContractID");
		tList[0].addLiteral2Post("ContractAtCarIDEqualCarAtCarID");
		tList[0].addLiteral2Post("CarAtLevel");
		tList[0].addLiteral2Post("CarAtManufacturer");
		tList[0].addLiteral2Post("CarAtModel");
		tList[0].addLiteral2Post("CarAtSerialNumber");
		tList[0].addLiteral2Post("CarAtColor");
		// tList[1].postCondition = tList[1].getFormula4Post(logic);
		tList[0].setPostCondition((Formula) logic.createExpression(
				"CarIsBooked & ContractIsOpened & ContractAtCarID & ContractAtContractID & ContractAtCarIDEqualCarAtCarID & CarAtLevel & CarAtManufacturer & CarAtModel & CarAtSerialNumber & CarAtColor"));

		tList[0].addLiteral2PostComma("ContractAtCarID");
		tList[0].addLiteral2PostComma("ContractAtContractID");
		tList[0].addLiteral2PostComma("ContractAtCarIDEqualCarAtCarID");
		tList[0].addLiteral2PostComma("CarAtLevel");
		tList[0].addLiteral2PostComma("CarAtManufacturer");
		tList[0].addLiteral2PostComma("CarAtModel");
		tList[0].addLiteral2PostComma("CarAtSerialNumber");
		tList[0].addLiteral2PostComma("CarAtColor");
		// tList[1].postComma = tList[1].getFormula4PostComma(logic);
		tList[0].setPostComma((Formula) logic.createExpression(
				"ContractAtCarID & ContractAtContractID & ContractAtCarIDEqualCarAtCarID & CarAtLevel & CarAtManufacturer & CarAtModel & CarAtSerialNumber & CarAtColor"));

		tList[0].addLiteral2Instance("ContractAtCarAgeEqualOrBiggerThan2");
		tList[0].addLiteral2Instance("CarAtLevelEqualLuxury");
		tList[0].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
		// tList[1].instance = tList[1].getFormula4Instance(logic);
		tList[0].setInstance((Formula) logic.createExpression(
			"ContractAtCarAgeEqualOrBiggerThan2 & CarAtLevelEqualLuxury & ContractAtCarMileageSmallerThan30000"));

		tList[0].artifacts = new ArrayList<Artifact>();
		tList[0].artifacts.add(artCar);
		tList[0].artifacts.add(artContract);
		tList[0].service = "reserveACar(Car,Contract)";

		tList[1] = new BusinessTask();
		tList[1].name = "Service Booked Vehicles";

		tList[1].addLiteral2Pre("CarIsBooked");
		tList[1].addLiteral2Pre("ContractIsOpened");
		tList[1].addLiteral2Pre("~ContractAtPreRentCondition");
		tList[1].addLiteral2Pre("ContractAtCarIDEqualCarAtCarID");
		// tList[2].preCondition = tList[2].getFormula4Pre(logic);
		tList[1].setPreCondition((Formula) logic.createExpression(
				"CarIsBooked & ContractIsOpened & (~ContractAtPreRentCondition) & ContractAtCarIDEqualCarAtCarID"));

		tList[1].addLiteral2Post("ContractAtPreRentCondition");
		tList[1].addLiteral2Post("CarIsBookedAndServiced");
		// tList[2].postCondition = tList[2].getFormula4Post(logic);
		tList[1].setPostCondition((Formula) logic
				.createExpression("ContractAtPreRentCondition & CarIsBookedAndServiced"));

		tList[1].addLiteral2PostComma("ContractAtPreRentCondition");
		// tList[2].postComma = tList[2].getFormula4PostComma(logic);
		tList[1].setPostComma((Formula) logic.createExpression("ContractAtPreRentCondition"));

		tList[1].addLiteral2Instance("ContractAtCarAgeEqualOrBiggerThan2");
		tList[1].addLiteral2Instance("CarAtLevelEqualLuxury");
		tList[1].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
		// tList[2].instance = tList[2].getFormula4Instance(logic);
		tList[1].setInstance((Formula) logic.createExpression(
				"ContractAtCarAgeEqualOrBiggerThan2 & CarAtLevelEqualLuxury & ContractAtCarMileageSmallerThan30000"));

		tList[1].artifacts = new ArrayList<Artifact>();
		tList[1].artifacts.add(artCar);
		tList[1].artifacts.add(artContract);
		tList[1].service = "serviceBookedVehicles(Car,Contract)";

		tList[2] = new BusinessTask();
		tList[2].name = "Procure Booking";

		tList[2].addLiteral2Pre("ContractIsOpened");
		tList[2].addLiteral2Pre("~ContractAtCustomerID");
		tList[2].addLiteral2Pre("ContractAtCarID");
		// tList[3].preCondition = tList[3].getFormula4Pre(logic);
		tList[2].setPreCondition((Formula) logic
				.createExpression("ContractIsOpened & ~ContractAtCustomerID & ContractAtCarID"));

		tList[2].addLiteral2Post("ContractAtCustomerID");
		tList[2].addLiteral2Post("ContractAtInsuranceType");
		tList[2].addLiteral2Post("ContractAtExpectedPickedupTime");
		tList[2].addLiteral2Post("ContractAtExpectedReturnedTime");
		tList[2].addLiteral2Post("CustomerAtCustomerID");
		tList[2].addLiteral2Post("CustomerAtName");
		tList[2].addLiteral2Post("CustomerAtAge");
		tList[2].addLiteral2Post("CustomerAtGender");
		tList[2].addLiteral2Post("CustomerAtAddress");
		tList[2].addLiteral2Post("CustomerAtLicenseNumber");
		tList[2].addLiteral2Post("CustomerAtDriverLicensePeriod");
		tList[2].addLiteral2Post("CustomerAtExpiredDate");
		tList[2].addLiteral2Post("ContractAtCustomerIDEqualCustomerAtCustomerID");
		// tList[3].postCondition = tList[3].getFormula4Post(logic);
		tList[2].setPostCondition((Formula) logic.createExpression(
				"ContractAtCustomerID & ContractAtInsuranceType & ContractAtExpectedPickedupTime & ContractAtExpectedReturnedTime & CustomerAtCustomerID & CustomerAtName & CustomerAtAge & CustomerAtGender & CustomerAtAddress & CustomerAtLicenseNumber & CustomerAtDriverLicensePeriod & CustomerAtExpiredDate & ContractAtCustomerIDEqualCustomerAtCustomerID"));

		tList[2].addLiteral2PostComma("ContractAtCustomerID");
		tList[2].addLiteral2PostComma("ContractAtInsuranceType");
		tList[2].addLiteral2PostComma("ContractAtExpectedPickedupTime");
		tList[2].addLiteral2PostComma("ContractAtExpectedReturnedTime");
		tList[2].addLiteral2PostComma("CustomerAtCustomerID");
		tList[2].addLiteral2PostComma("CustomerAtName");
		tList[2].addLiteral2PostComma("CustomerAtAge");
		tList[2].addLiteral2PostComma("CustomerAtGender");
		tList[2].addLiteral2PostComma("CustomerAtAddress");
		tList[2].addLiteral2PostComma("CustomerAtLicenseNumber");
		tList[2].addLiteral2PostComma("CustomerAtDriverLicensePeriod");
		tList[2].addLiteral2PostComma("CustomerAtExpiredDate");
		tList[2].addLiteral2PostComma("ContractAtCustomerIDEqualCustomerAtCustomerID");
		// tList[3].postComma = tList[3].getFormula4PostComma(logic);
		tList[2].setPostComma((Formula) logic.createExpression(
				"ContractAtCustomerID & ContractAtInsuranceType & ContractAtExpectedPickedupTime & ContractAtExpectedReturnedTime & CustomerAtCustomerID & CustomerAtName & CustomerAtAge & CustomerAtGender & CustomerAtAddress & CustomerAtLicenseNumber & CustomerAtDriverLicensePeriod & CustomerAtExpiredDate & ContractAtCustomerIDEqualCustomerAtCustomerID"));

		tList[2].artifacts = new ArrayList<Artifact>();
		tList[2].artifacts.add(artCustomer);
		tList[2].artifacts.add(artContract);
		tList[2].service = "procureBooking(Contract,Customer)";

		tList[3] = new BusinessTask();
		tList[3].name = "Registration";

		tList[3].addLiteral2Pre("ContractIsOpened");
		tList[3].addLiteral2Pre("~ContractAtDepositedType");
		tList[3].addLiteral2Pre("~ContractAtDepositedBudget");
		// tList[4].preCondition = tList[4].getFormula4Pre(logic);
		tList[3].setPreCondition((Formula) logic
				.createExpression("ContractIsOpened & (~ContractAtDepositedType) & (~ContractAtDepositedBudget)"));

		tList[3].addLiteral2Post("ContractIsDeposited");
		tList[3].addLiteral2Post("ContractAtDepositedType");
		tList[3].addLiteral2Post("ContractAtDepositedBudget");
		// tList[4].postCondition = tList[4].getFormula4Post(logic);
		tList[3].setPostCondition((Formula) logic
				.createExpression("ContractIsDeposited & ContractAtDepositedType & ContractAtDepositedBudget"));

		tList[3].addLiteral2PostComma("ContractAtDepositedType");
		tList[3].addLiteral2PostComma("ContractAtDepositedBudget");
		// tList[4].postComma = tList[4].getFormula4PostComma(logic);
		tList[3].setPostComma((Formula) logic.createExpression("ContractAtDepositedType & ContractAtDepositedBudget"));

		tList[3].artifacts = new ArrayList<Artifact>();
		tList[3].artifacts.add(artContract);
		tList[3].service = "Registration(Contract)";

		tList[4] = new BusinessTask();
		tList[4].name = "Pre-rent Check";

		tList[4].addLiteral2Pre("CarIsBookedAndServiced");
		tList[4].addLiteral2Pre("ContractIsDeposited");
		tList[4].addLiteral2Pre("ContractAtCarIDEqualCarAtCarID");
		tList[4].setPreCondition((Formula) logic
				.createExpression("CarIsBookedAndServiced & ContractIsDeposited & ContractAtCarIDEqualCarAtCarID"));

		tList[4].addLiteral2Post("ContractAtPreRentCondition");
		tList[4].addLiteral2Post("CarIsReadyForRent");
		tList[4].setPostCondition((Formula) logic.createExpression("ContractAtPreRentCondition & CarIsReadyForRent"));

		tList[4].addLiteral2PostComma("ContractAtPreRentCondition");
		tList[4].setPostComma((Formula) logic.createExpression("ContractAtPreRentCondition"));

		tList[4].addLiteral2Instance("ContractAtCarAgeEqualOrBiggerThan2");
		tList[4].addLiteral2Instance("CarAtLevelEqualLuxury");
		tList[4].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
		tList[4].setInstance((Formula) logic.createExpression(
				"ContractAtCarAgeEqualOrBiggerThan2 & CarAtLevelEqualLuxury & ContractAtCarMileageSmallerThan30000"));

		tList[4].artifacts = new ArrayList<Artifact>();
		tList[4].artifacts.add(artCar);
		tList[4].artifacts.add(artContract);
		tList[4].service = "preRentCheck(Car,Contract)";

		tList[5] = new BusinessTask();
		tList[5].name = "Rental Approval";

		tList[5].addLiteral2Pre("ContractIsOpened");
		tList[5].addLiteral2Pre("ContractAtCustomerID");
		tList[5].addLiteral2Pre("ContractAtCarID");
		tList[5].setPreCondition((Formula) logic
				.createExpression("ContractIsOpened & ContractAtCustomerID & ContractAtCarID"));

		tList[5].addLiteral2Post("ContractAtApproval");
		tList[5].setPostCondition((Formula) logic.createExpression("ContractAtApproval"));

		tList[5].addLiteral2PostComma("ContractAtApproval");
		tList[5].setPostComma((Formula) logic.createExpression("ContractAtApproval"));

		tList[5].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
		tList[5].setInstance((Formula) logic.createExpression("ContractAtCarMileageSmallerThan30000"));

		tList[5].addLiteral2Dom("CustomerAtAgeIn18To30");
		tList[5].addLiteral2Dom("CustomerAtGenderEqualMale");
		tList[5].addLiteral2Dom("CustomerAtJobEqualUnidentified");
		tList[5].addLiteral2Dom("CustomerAtDriverLicensePeriodIn0To2");
		tList[5].addLiteral2Dom("ContractAtInsuranceTypeEqualBreakdown");
		tList[5].addLiteral2Dom("CarAtLevelEqualLuxury");
		tList[5].addLiteral2Dom("CarAtModelEqualX6");
		tList[5].setDom((Formula) logic.createExpression(
				"CustomerAtAgeIn18To30 & CustomerAtGenderEqualMale & CustomerAtJobEqualUnidentified & CustomerAtDriverLicensePeriodIn0To2 & ContractAtInsuranceTypeEqualBreakdown & CarAtLevelEqualLuxury & CarAtModelEqualX6"));

		tList[5].artifacts = new ArrayList<Artifact>();
		tList[5].artifacts.add(artContract);
		tList[5].service = "approveRent(Contract)";

		tList[6] = new BusinessTask();
		tList[6].name = "Cancel Rent";

		tList[6].addLiteral2Pre("ContractIsOpened");
		tList[6].addLiteral2Pre("CarIsBooked");
		tList[6].addLiteral2Pre("ContractAtApprovalIsDenied");
		tList[6].addLiteral2Pre("ContractAtCarIDEqualCarAtCarID");
		tList[6].setPreCondition((Formula) logic.createExpression(
				"ContractIsOpened & CarIsBooked & ContractAtApprovalIsDenied & ContractAtCarIDEqualCarAtCarID"));

		tList[6].addLiteral2Post("ContractIsCanceled");
		tList[6].addLiteral2Post("CarIsNonBooked");
		tList[6].addLiteral2Post("ContractAtApprovalIsDenied");
		tList[6].setPostCondition((Formula) logic
				.createExpression("ContractIsCanceled & CarIsNonBooked & ContractAtApprovalIsDenied"));

		tList[6].addLiteral2PostComma("ContractAtApprovalIsDenied");
		tList[6].setPostComma((Formula) logic.createExpression("ContractAtApprovalIsDenied"));

		tList[6].addLiteral2Instance("ContractAtCarAgeEqualOrBiggerThan2");
		tList[6].addLiteral2Instance("CarAtLevelEqualLuxury");
		tList[6].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
		tList[6].setInstance((Formula) logic.createExpression(
				"ContractAtCarAgeEqualOrBiggerThan2 & CarAtLevelEqualLuxury & ContractAtCarMileageSmallerThan30000"));

		tList[6].artifacts = new ArrayList<Artifact>();
		tList[6].artifacts.add(artCar);
		tList[6].artifacts.add(artContract);
		tList[6].service = "cancelRent(Contract,Car)";
		taskList = Arrays.asList(tList);
		String expected = "Service Booked Vehicles, Pre-rent Check, ";
		//Component mockComponent = 
		assertEquals(expected, Algorithm.elimination(listCond, listCons, label, tList));
	}
	public void testTest() throws Exception {
		assertEquals("5", Integer.toString(3+2));
	}
//	public void testReordering() throws Exception {
//		List<Expression> listCond = new ArrayList<Expression>();
//		listCond.add(new Expression("Customer", "Age", "EqualOrBiggerThan", null, null, "2"));
//		listCond.add(new Expression("Customer", "Gender", "Equal", null, null, "Male"));
//		listCond.add(new Expression("Customer", "Job", "Equal", null, null, "Unidentified"));
//		listCond.add(new Expression("Customer", "DriverLicensePeriod", "EqualOrBiggerThan", null, null, "2"));
//		listCond.add(new Expression("Customer", "InsuranceType", "Equal", null, null, "BreakDown"));
//		listCond.add(new Expression("Car", "Level", "Equal", null, null, "Luxury"));
//		listCond.add(new Expression("Car", "Model", "Equal", null, null, "X6"));
//		List<Expression> listCons = new ArrayList<Expression>();
//		listCons.add(new Expression("Contract", "PostRentAssment", "Equal", null, null, "Avoided"));
//		
//		int[] label = {1, 3, 4, 5, 6, 7};
//		
//		Artifact artContract = new Artifact("Contract");
//		Artifact artCar = new Artifact("Car");
//		Artifact artCustomer = new Artifact("Customer");
//		List<BusinessTask> taskList = new ArrayList<BusinessTask>();
//		BusinessTask[] tList = new BusinessTask[7];
//		tList[0] = new BusinessTask();
//		tList[0].name = "Reserve";
//
//		tList[0].addLiteral2Pre("CarIsNonBooked");
//		tList[0].addLiteral2Pre("ContractIsInitial");
//		tList[0].addLiteral2Pre("~ContractAtCarID");
//		tList[0].addLiteral2Pre("~ContractAtContractID");
//		// tList[1].preCondition = tList[1].getFormula4Pre(logic);
//		tList[0].setPreCondition((Formula) logic
//				.createExpression("CarIsNonBooked & ContractIsInitial & (~ContractAtCarID) & ~ContractAtContractID"));
//
//		tList[0].addLiteral2Post("CarIsBooked");
//		tList[0].addLiteral2Post("ContractIsOpened");
//		tList[0].addLiteral2Post("ContractAtCarID");
//		tList[0].addLiteral2Post("ContractAtContractID");
//		tList[0].addLiteral2Post("ContractAtCarIDEqualCarAtCarID");
//		tList[0].addLiteral2Post("CarAtLevel");
//		tList[0].addLiteral2Post("CarAtManufacturer");
//		tList[0].addLiteral2Post("CarAtModel");
//		tList[0].addLiteral2Post("CarAtSerialNumber");
//		tList[0].addLiteral2Post("CarAtColor");
//		// tList[1].postCondition = tList[1].getFormula4Post(logic);
//		tList[0].setPostCondition((Formula) logic.createExpression(
//				"CarIsBooked & ContractIsOpened & ContractAtCarID & ContractAtContractID & ContractAtCarIDEqualCarAtCarID & CarAtLevel & CarAtManufacturer & CarAtModel & CarAtSerialNumber & CarAtColor"));
//
//		tList[0].addLiteral2PostComma("ContractAtCarID");
//		tList[0].addLiteral2PostComma("ContractAtContractID");
//		tList[0].addLiteral2PostComma("ContractAtCarIDEqualCarAtCarID");
//		tList[0].addLiteral2PostComma("CarAtLevel");
//		tList[0].addLiteral2PostComma("CarAtManufacturer");
//		tList[0].addLiteral2PostComma("CarAtModel");
//		tList[0].addLiteral2PostComma("CarAtSerialNumber");
//		tList[0].addLiteral2PostComma("CarAtColor");
//		// tList[1].postComma = tList[1].getFormula4PostComma(logic);
//		tList[0].setPostComma((Formula) logic.createExpression(
//				"ContractAtCarID & ContractAtContractID & ContractAtCarIDEqualCarAtCarID & CarAtLevel & CarAtManufacturer & CarAtModel & CarAtSerialNumber & CarAtColor"));
//
//		tList[0].addLiteral2Instance("ContractAtCarAgeEqualOrBiggerThan2");
//		tList[0].addLiteral2Instance("CarAtLevelEqualLuxury");
//		tList[0].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
//		// tList[1].instance = tList[1].getFormula4Instance(logic);
//		tList[0].setInstance((Formula) logic.createExpression(
//			"ContractAtCarAgeEqualOrBiggerThan2 & CarAtLevelEqualLuxury & ContractAtCarMileageSmallerThan30000"));
//
//		tList[0].artifacts = new ArrayList<Artifact>();
//		tList[0].artifacts.add(artCar);
//		tList[0].artifacts.add(artContract);
//		tList[0].service = "reserveACar(Car,Contract)";
//
//		tList[1] = new BusinessTask();
//		tList[1].name = "Service Booked Vehicles";
//
//		tList[1].addLiteral2Pre("CarIsBooked");
//		tList[1].addLiteral2Pre("ContractIsOpened");
//		tList[1].addLiteral2Pre("~ContractAtPreRentCondition");
//		tList[1].addLiteral2Pre("ContractAtCarIDEqualCarAtCarID");
//		// tList[2].preCondition = tList[2].getFormula4Pre(logic);
//		tList[1].setPreCondition((Formula) logic.createExpression(
//				"CarIsBooked & ContractIsOpened & (~ContractAtPreRentCondition) & ContractAtCarIDEqualCarAtCarID"));
//
//		tList[1].addLiteral2Post("ContractAtPreRentCondition");
//		tList[1].addLiteral2Post("CarIsBookedAndServiced");
//		// tList[2].postCondition = tList[2].getFormula4Post(logic);
//		tList[1].setPostCondition((Formula) logic
//				.createExpression("ContractAtPreRentCondition & CarIsBookedAndServiced"));
//
//		tList[1].addLiteral2PostComma("ContractAtPreRentCondition");
//		// tList[2].postComma = tList[2].getFormula4PostComma(logic);
//		tList[1].setPostComma((Formula) logic.createExpression("ContractAtPreRentCondition"));
//
//		tList[1].addLiteral2Instance("ContractAtCarAgeEqualOrBiggerThan2");
//		tList[1].addLiteral2Instance("CarAtLevelEqualLuxury");
//		tList[1].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
//		// tList[2].instance = tList[2].getFormula4Instance(logic);
//		tList[1].setInstance((Formula) logic.createExpression(
//				"ContractAtCarAgeEqualOrBiggerThan2 & CarAtLevelEqualLuxury & ContractAtCarMileageSmallerThan30000"));
//
//		tList[1].artifacts = new ArrayList<Artifact>();
//		tList[1].artifacts.add(artCar);
//		tList[1].artifacts.add(artContract);
//		tList[1].service = "serviceBookedVehicles(Car,Contract)";
//
//		tList[2] = new BusinessTask();
//		tList[2].name = "Procure Booking";
//
//		tList[2].addLiteral2Pre("ContractIsOpened");
//		tList[2].addLiteral2Pre("~ContractAtCustomerID");
//		tList[2].addLiteral2Pre("ContractAtCarID");
//		// tList[3].preCondition = tList[3].getFormula4Pre(logic);
//		tList[2].setPreCondition((Formula) logic
//				.createExpression("ContractIsOpened & ~ContractAtCustomerID & ContractAtCarID"));
//
//		tList[2].addLiteral2Post("ContractAtCustomerID");
//		tList[2].addLiteral2Post("ContractAtInsuranceType");
//		tList[2].addLiteral2Post("ContractAtExpectedPickedupTime");
//		tList[2].addLiteral2Post("ContractAtExpectedReturnedTime");
//		tList[2].addLiteral2Post("CustomerAtCustomerID");
//		tList[2].addLiteral2Post("CustomerAtName");
//		tList[2].addLiteral2Post("CustomerAtAge");
//		tList[2].addLiteral2Post("CustomerAtGender");
//		tList[2].addLiteral2Post("CustomerAtAddress");
//		tList[2].addLiteral2Post("CustomerAtLicenseNumber");
//		tList[2].addLiteral2Post("CustomerAtDriverLicensePeriod");
//		tList[2].addLiteral2Post("CustomerAtExpiredDate");
//		tList[2].addLiteral2Post("ContractAtCustomerIDEqualCustomerAtCustomerID");
//		// tList[3].postCondition = tList[3].getFormula4Post(logic);
//		tList[2].setPostCondition((Formula) logic.createExpression(
//				"ContractAtCustomerID & ContractAtInsuranceType & ContractAtExpectedPickedupTime & ContractAtExpectedReturnedTime & CustomerAtCustomerID & CustomerAtName & CustomerAtAge & CustomerAtGender & CustomerAtAddress & CustomerAtLicenseNumber & CustomerAtDriverLicensePeriod & CustomerAtExpiredDate & ContractAtCustomerIDEqualCustomerAtCustomerID"));
//
//		tList[2].addLiteral2PostComma("ContractAtCustomerID");
//		tList[2].addLiteral2PostComma("ContractAtInsuranceType");
//		tList[2].addLiteral2PostComma("ContractAtExpectedPickedupTime");
//		tList[2].addLiteral2PostComma("ContractAtExpectedReturnedTime");
//		tList[2].addLiteral2PostComma("CustomerAtCustomerID");
//		tList[2].addLiteral2PostComma("CustomerAtName");
//		tList[2].addLiteral2PostComma("CustomerAtAge");
//		tList[2].addLiteral2PostComma("CustomerAtGender");
//		tList[2].addLiteral2PostComma("CustomerAtAddress");
//		tList[2].addLiteral2PostComma("CustomerAtLicenseNumber");
//		tList[2].addLiteral2PostComma("CustomerAtDriverLicensePeriod");
//		tList[2].addLiteral2PostComma("CustomerAtExpiredDate");
//		tList[2].addLiteral2PostComma("ContractAtCustomerIDEqualCustomerAtCustomerID");
//		// tList[3].postComma = tList[3].getFormula4PostComma(logic);
//		tList[2].setPostComma((Formula) logic.createExpression(
//				"ContractAtCustomerID & ContractAtInsuranceType & ContractAtExpectedPickedupTime & ContractAtExpectedReturnedTime & CustomerAtCustomerID & CustomerAtName & CustomerAtAge & CustomerAtGender & CustomerAtAddress & CustomerAtLicenseNumber & CustomerAtDriverLicensePeriod & CustomerAtExpiredDate & ContractAtCustomerIDEqualCustomerAtCustomerID"));
//
//		tList[2].artifacts = new ArrayList<Artifact>();
//		tList[2].artifacts.add(artCustomer);
//		tList[2].artifacts.add(artContract);
//		tList[2].service = "procureBooking(Contract,Customer)";
//
//		tList[3] = new BusinessTask();
//		tList[3].name = "Registration";
//
//		tList[3].addLiteral2Pre("ContractIsOpened");
//		tList[3].addLiteral2Pre("~ContractAtDepositedType");
//		tList[3].addLiteral2Pre("~ContractAtDepositedBudget");
//		// tList[4].preCondition = tList[4].getFormula4Pre(logic);
//		tList[3].setPreCondition((Formula) logic
//				.createExpression("ContractIsOpened & (~ContractAtDepositedType) & (~ContractAtDepositedBudget)"));
//
//		tList[3].addLiteral2Post("ContractIsDeposited");
//		tList[3].addLiteral2Post("ContractAtDepositedType");
//		tList[3].addLiteral2Post("ContractAtDepositedBudget");
//		// tList[4].postCondition = tList[4].getFormula4Post(logic);
//		tList[3].setPostCondition((Formula) logic
//				.createExpression("ContractIsDeposited & ContractAtDepositedType & ContractAtDepositedBudget"));
//
//		tList[3].addLiteral2PostComma("ContractAtDepositedType");
//		tList[3].addLiteral2PostComma("ContractAtDepositedBudget");
//		// tList[4].postComma = tList[4].getFormula4PostComma(logic);
//		tList[3].setPostComma((Formula) logic.createExpression("ContractAtDepositedType & ContractAtDepositedBudget"));
//
//		tList[3].artifacts = new ArrayList<Artifact>();
//		tList[3].artifacts.add(artContract);
//		tList[3].service = "Registration(Contract)";
//
//		tList[4] = new BusinessTask();
//		tList[4].name = "Pre-rent Check";
//
//		tList[4].addLiteral2Pre("CarIsBookedAndServiced");
//		tList[4].addLiteral2Pre("ContractIsDeposited");
//		tList[4].addLiteral2Pre("ContractAtCarIDEqualCarAtCarID");
//		tList[4].setPreCondition((Formula) logic
//				.createExpression("CarIsBookedAndServiced & ContractIsDeposited & ContractAtCarIDEqualCarAtCarID"));
//
//		tList[4].addLiteral2Post("ContractAtPreRentCondition");
//		tList[4].addLiteral2Post("CarIsReadyForRent");
//		tList[4].setPostCondition((Formula) logic.createExpression("ContractAtPreRentCondition & CarIsReadyForRent"));
//
//		tList[4].addLiteral2PostComma("ContractAtPreRentCondition");
//		tList[4].setPostComma((Formula) logic.createExpression("ContractAtPreRentCondition"));
//
//		tList[4].addLiteral2Instance("ContractAtCarAgeEqualOrBiggerThan2");
//		tList[4].addLiteral2Instance("CarAtLevelEqualLuxury");
//		tList[4].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
//		tList[4].setInstance((Formula) logic.createExpression(
//				"ContractAtCarAgeEqualOrBiggerThan2 & CarAtLevelEqualLuxury & ContractAtCarMileageSmallerThan30000"));
//
//		tList[4].artifacts = new ArrayList<Artifact>();
//		tList[4].artifacts.add(artCar);
//		tList[4].artifacts.add(artContract);
//		tList[4].service = "preRentCheck(Car,Contract)";
//
//		tList[5] = new BusinessTask();
//		tList[5].name = "Rental Approval";
//
//		tList[5].addLiteral2Pre("ContractIsOpened");
//		tList[5].addLiteral2Pre("ContractAtCustomerID");
//		tList[5].addLiteral2Pre("ContractAtCarID");
//		tList[5].setPreCondition((Formula) logic
//				.createExpression("ContractIsOpened & ContractAtCustomerID & ContractAtCarID"));
//
//		tList[5].addLiteral2Post("ContractAtApproval");
//		tList[5].setPostCondition((Formula) logic.createExpression("ContractAtApproval"));
//
//		tList[5].addLiteral2PostComma("ContractAtApproval");
//		tList[5].setPostComma((Formula) logic.createExpression("ContractAtApproval"));
//
//		tList[5].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
//		tList[5].setInstance((Formula) logic.createExpression("ContractAtCarMileageSmallerThan30000"));
//
//		tList[5].addLiteral2Dom("CustomerAtAgeIn18To30");
//		tList[5].addLiteral2Dom("CustomerAtGenderEqualMale");
//		tList[5].addLiteral2Dom("CustomerAtJobEqualUnidentified");
//		tList[5].addLiteral2Dom("CustomerAtDriverLicensePeriodIn0To2");
//		tList[5].addLiteral2Dom("ContractAtInsuranceTypeEqualBreakdown");
//		tList[5].addLiteral2Dom("CarAtLevelEqualLuxury");
//		tList[5].addLiteral2Dom("CarAtModelEqualX6");
//		tList[5].setDom((Formula) logic.createExpression(
//				"CustomerAtAgeIn18To30 & CustomerAtGenderEqualMale & CustomerAtJobEqualUnidentified & CustomerAtDriverLicensePeriodIn0To2 & ContractAtInsuranceTypeEqualBreakdown & CarAtLevelEqualLuxury & CarAtModelEqualX6"));
//
//		tList[5].artifacts = new ArrayList<Artifact>();
//		tList[5].artifacts.add(artContract);
//		tList[5].service = "approveRent(Contract)";
//
//		tList[6] = new BusinessTask();
//		tList[6].name = "Cancel Rent";
//
//		tList[6].addLiteral2Pre("ContractIsOpened");
//		tList[6].addLiteral2Pre("CarIsBooked");
//		tList[6].addLiteral2Pre("ContractAtApprovalIsDenied");
//		tList[6].addLiteral2Pre("ContractAtCarIDEqualCarAtCarID");
//		tList[6].setPreCondition((Formula) logic.createExpression(
//				"ContractIsOpened & CarIsBooked & ContractAtApprovalIsDenied & ContractAtCarIDEqualCarAtCarID"));
//
//		tList[6].addLiteral2Post("ContractIsCanceled");
//		tList[6].addLiteral2Post("CarIsNonBooked");
//		tList[6].addLiteral2Post("ContractAtApprovalIsDenied");
//		tList[6].setPostCondition((Formula) logic
//				.createExpression("ContractIsCanceled & CarIsNonBooked & ContractAtApprovalIsDenied"));
//
//		tList[6].addLiteral2PostComma("ContractAtApprovalIsDenied");
//		tList[6].setPostComma((Formula) logic.createExpression("ContractAtApprovalIsDenied"));
//
//		tList[6].addLiteral2Instance("ContractAtCarAgeEqualOrBiggerThan2");
//		tList[6].addLiteral2Instance("CarAtLevelEqualLuxury");
//		tList[6].addLiteral2Instance("ContractAtCarMileageSmallerThan30000");
//		tList[6].setInstance((Formula) logic.createExpression(
//				"ContractAtCarAgeEqualOrBiggerThan2 & CarAtLevelEqualLuxury & ContractAtCarMileageSmallerThan30000"));
//
//		tList[6].artifacts = new ArrayList<Artifact>();
//		tList[6].artifacts.add(artCar);
//		tList[6].artifacts.add(artContract);
//		tList[6].service = "cancelRent(Contract,Car)";
//		taskList = Arrays.asList(tList);
//		String expected = "Rental Approval-Procure Booking";
//		String result = Algorithm.reordering(listCond, listCons, label, tList);
//		assertEquals(expected, result);
//	}
}
