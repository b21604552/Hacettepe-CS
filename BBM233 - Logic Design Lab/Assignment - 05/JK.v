`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    23:23:13 12/15/2018 
// Design Name: 
// Module Name:    JK 
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
module JK(J, K, CLK, Q);
	input J, K, CLK;
	output reg Q = 1;
	always @ (negedge CLK)
		begin
			if(J==0 & K==1)
			begin
				Q=0;
			end
			else if(J==1 & K==0)
			begin
				Q=1;
			end
			else if(J==1 & K==1)
			begin
				Q=!Q;
			end
		end
endmodule
