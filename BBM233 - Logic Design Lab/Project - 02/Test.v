`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   20:14:03 12/29/2018
// Design Name:   VendingMachine
// Module Name:   C:/FirstAssignment/LabProject2/Test.v
// Project Name:  LabProject2
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: VendingMachine
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module Test;

	// Inputs
	reg Req;
	reg OneTL;
	reg HalfTL;
	reg Coke;
	reg Water;
	reg Clk;

	// Outputs
	wire Change1;
	wire Change05;
	wire G_Coke;
	wire G_Water;

	// Instantiate the Unit Under Test (UUT)
	VendingMachine uut (
		.Req(Req), 
		.OneTL(OneTL), 
		.HalfTL(HalfTL), 
		.Change1(Change1), 
		.Change05(Change05), 
		.Coke(Coke), 
		.Water(Water), 
		.Clk(Clk), 
		.G_Coke(G_Coke), 
		.G_Water(G_Water)
	);

	initial begin
		// 0,5 Lira Water.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		Clk = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 1;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 1;
		#10;
		// 1 Lira Coke.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 1;
		Water = 0;
		#10;
		// 2 Lira Water.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 1;
		#10;
		// 2 Lira Water.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 1;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 1;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 1;
		#10;
		// 1,5 Lira Water.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 1;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 1;
		#10;
		// 2 Lira Coke.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 1;
		Water = 0;
		#10;
		// 2,5 Lira Coke.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 1;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 1;
		Water = 0;
		#10;
		// 2,5 Lira Water.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 1;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 1;
		#10;
		// 3 Lira Coke.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 1;
		Water = 0;
		#10;
		// 3 Lira Water.
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 1;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
		Req = 0;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 1;
		#10;
		//Stop
		Req = 1;
		OneTL = 0;
		HalfTL = 0;
		Coke = 0;
		Water = 0;
		#10;
	end
   always #5 Clk=!Clk;
      
endmodule

