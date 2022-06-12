`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   07:10:57 11/25/2018
// Design Name:   Subtr
// Module Name:   C:/FirstAssignment/FirstProject/Result.v
// Project Name:  FirstProject
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: Subtr
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module Result;

	// Inputs
	reg [7:0] A;
	reg [7:0] B;
	reg Cin;
	reg Mod;

	// Outputs
	wire Cout;
	wire [7:0] S;

	// Instantiate the Unit Under Test (UUT)
	Subtr uut (
		.A(A), 
		.B(B), 
		.Cin(Cin), 
		.Cout(Cout), 
		.S(S), 
		.Mod(Mod)
	);

	initial begin
		// A>B
		A = 8'b10111001;B = 8'b10001111;Cin = 0;Mod = 1;
		#75;
		A = 8'b11010101;B = 8'b11010100;Cin = 0;Mod =1;
		#75;
		A = 8'b11100110;B = 8'b00011010;Cin = 0;Mod =1;
		#75;
		A = 8'b10011100;B = 8'b01000011;Cin = 0;Mod =1;
		#75;
		// A<B
		A = 8'b10111001;B = 8'b11100101;Cin = 0;Mod =1;
		#75;
		A = 8'b11001010;B = 8'b11110011;Cin = 0;Mod =1;
		#75;
		A = 8'b10000111;B = 8'b11011010;Cin = 0;Mod =1;
		#75;
		A = 8'b10110111;B = 8'b11000000;Cin = 0;Mod =1;
		#75;
		// A=B
		A = 8'b10011010;B = 8'b10011010;Cin = 0;Mod =1;
		#75;
		A = 8'b10010111;B = 8'b10010111;Cin = 0;Mod =1;
		#75;
		A = 8'b00101110;B = 8'b00101110;Cin = 0;Mod =1;
		#75;
		A = 8'b11001100;B = 8'b11001100;Cin = 0;Mod =1;
		#75;
	end
      
endmodule

