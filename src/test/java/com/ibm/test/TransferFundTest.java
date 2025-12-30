package com.ibm.test;

import org.testng.annotations.Test;

public class TransferFundTest {
  @Test
  public void verifyFundsWithEnoughFunds() {
	  System.out.println("Funds transfer successfully.....validated");
  }
  
  @Test(dependsOnMethods = {"verifyFundsWithEnoughFunds"})
  public void VerifyFundsTransferWithNotEnoughFunds() {
	  System.out.println("Funds transfer failde...add funds");
  }
}
