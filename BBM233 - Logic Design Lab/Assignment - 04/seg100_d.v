`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    06:11:59 12/12/2018 
// Design Name: 
// Module Name:    sequence_detector 
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
module D_FlipFLop( Q, D, CLK);
	output reg Q;
	input D, CLK;
	always @(posedge CLK)
			Q = D;
endmodule
module sequence_detector(Y,CLK,X);
	input CLK;
	input X;
	output Y;
	wire W1,W2,W3;
	D_FlipFLop first (.Q(W1),.D(X),.CLK(CLK));
	assign W2 = W1 && ~X;
	D_FlipFLop second(.Q(W3),.D(W2),.CLK(CLK));
	assign Y= ~W1 && ~X && W3; 
endmodule
