`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   23:18:19 12/15/2018
// Design Name:   GrayCode
// Module Name:   C:/FirstAssignment/Lab6exp/GrayCodeTest.v
// Project Name:  Lab6exp
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: GrayCode
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module GrayCodeTest;
	// Inputs
	reg J;
	reg K;
	reg CLK = 1;
	// Outputs
	wire [3:0] QN;
	// Instantiate the Unit Under Test (UUT)
	GrayCode uut (
		.J(J), 
		.K(K), 
		.CLK(CLK),
		.QN(QN)
	);
	initial begin
		// Initialize Inputs
		J = 1;
		K = 1;
		CLK = 0;
		// Wait 100 ns for global reset to finish
		#100;
		// Add stimulus here
	end
      always #25 CLK=~CLK;
endmodule

