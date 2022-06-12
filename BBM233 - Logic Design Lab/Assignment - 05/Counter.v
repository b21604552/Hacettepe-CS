`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    23:22:04 12/15/2018 
// Design Name: 
// Module Name:    Counter 
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
module Counter(J,K,CLK,Q);
	input J,K,CLK;
	output wire[3:0]Q;
	JK JK0(J,K,CLK,Q[0]);
	JK JK1(J,K,Q[0],Q[1]);
	JK JK2(J,K,Q[1],Q[2]);
	JK JK3(J,K,Q[2],Q[3]);
endmodule
