`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    23:17:39 12/15/2018 
// Design Name: 
// Module Name:    GrayCode 
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

module GrayCode(J,K,CLK,QN);
	input J,K,CLK;
	wire[3:0]Q;
	output wire[3:0]QN;
	JK JK0(J,K,CLK,Q[0]);
	JK JK1(J,K,Q[0],Q[1]);
	JK JK2(J,K,Q[1],Q[2]);
	JK JK3(J,K,Q[2],Q[3]);
	assign QN[3] = Q[3];
	xor(QN[2],Q[2],Q[3]);
	xor(QN[1],Q[1],Q[2]);
	xor(QN[0],Q[0],Q[1]);
endmodule
