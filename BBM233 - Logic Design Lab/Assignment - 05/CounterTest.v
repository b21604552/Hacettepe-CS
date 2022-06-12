`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   23:24:07 12/15/2018
// Design Name:   Counter
// Module Name:   C:/FirstAssignment/Lab6exp/CounterTest.v
// Project Name:  Lab6exp
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: Counter
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module CounterTest;

	// Inputs
	reg CLK = 1;
	reg J,K;
	// Outputs
	wire [3:0] Q;
	Counter uut (
		.J(J),
		.K(K),
		.CLK(CLK), 
		.Q(Q)
	);
	initial begin
		J = 1;
		K = 1;
		CLK = 0;
		#100;
	end
      always #25 CLK=~CLK;
endmodule

