`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    17:41:33 11/13/2018 
// Design Name: 
// Module Name:    Subtr 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////

module FullAdder(A,B,Cin,S,Cout,Mod);
	input A,B,Cin,Mod;
	output Cout,S;
	wire xorBMod,xor_AB,and_AB,CinXorAB;
	xor(xorBMod,B,Mod);
	xor(xor_AB,A,xorBMod);
	xor(S,Cin,xor_AB);
	and(and_AB,A,xorBMod);
	and(CinXorAB,Cin,xor_AB);
	or(Cout,and_AB,CinXorAB);
endmodule

module Subtr(A,B,Cin,Cout,S,Mod,SW);
	output Cout;
	output [7:0] S;
	output [7:0] SW;
	input Cin,Mod;
	input [7:0] A,B;

	wire Co1,Co2,Co3,Co4,Co5,Co6,Co7,Co8;
	wire Cb1,Cb2,Cb3,Cb4,Cb5,Cb6,Cb7,Cb8;
	
	FullAdder F1(A[0],B[0],Cin,SW[0],Co1,Mod);
	FullAdder F2(A[1],B[1],Co1,SW[1],Co2,Mod);
	FullAdder F3(A[2],B[2],Co2,SW[2],Co3,Mod);
	FullAdder F4(A[3],B[3],Co3,SW[3],Co4,Mod);
	FullAdder F5(A[4],B[4],Co4,SW[4],Co5,Mod);
	FullAdder F6(A[5],B[5],Co5,SW[5],Co6,Mod);
	FullAdder F7(A[6],B[6],Co6,SW[6],Co7,Mod);
	FullAdder F8(A[7],B[7],Co7,SW[7],Cout,Mod);

	FullAdder F10(SW[0],1,0,S[0],Cb1,0);
	FullAdder F11(SW[1],0,Cb1,S[1],Cb2,0);
	FullAdder F12(SW[2],0,Cb2,S[2],Cb3,0);
	FullAdder F13(SW[3],0,Cb3,S[3],Cb4,0);
	FullAdder F14(SW[4],0,Cb4,S[4],Cb5,0);
	FullAdder F15(SW[5],0,Cb5,S[5],Cb6,0);
	FullAdder F16(SW[6],0,Cb6,S[6],Cb7,0);
	FullAdder F17(SW[7],0,Cb7,S[7],Cb8,0);
	
	Compare C15(S,Cout,Zero,Leq);
endmodule

module Compare(S,Cout,Zero,Leq);
	input [7:0] S;
	input Cout;
	output Zero,Leq;
	nor(Zero,S[0],S[1],S[2],S[3],S[4],S[5],S[6],S[7]);
	not(Leq,Cout);	
endmodule