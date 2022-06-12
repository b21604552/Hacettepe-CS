`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   06:15:23 12/12/2018
// Design Name:   sequence_detector
// Module Name:   C:/FirstAssignment/Lab5exp/DFFTest.v
// Project Name:  Lab5exp
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: sequence_detector
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module DFFTest;

	// Inputs
	reg CLK;
	reg X;

	// Outputs
	wire Y;

	// Instantiate the Unit Under Test (UUT)
	sequence_detector uut (
		.Y(Y), 
		.CLK(CLK), 
		.X(X)
	);

	initial begin
		// Initialize Inputs
		X = 0;
		CLK = 0;
		#100;
		X = 1;
		#100;
		X = 0;
		#100;
		X = 0;
		#100;
		X = 1;
		#100;
		X = 0;
		#100;
		X = 0;
		#100;
	end
   always #50 CLK = !CLK;
endmodule

