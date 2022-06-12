`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    20:12:29 12/24/2018 
// Design Name: 
// Module Name:    VendingMachine 
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
module VendingMachine(Req,OneTL,HalfTL,Change1,Change05,Coke,Water,Clk,G_Coke,G_Water);
input Req,OneTL,HalfTL,Coke,Water,Clk;
output G_Coke,G_Water,Change1,Change05;
reg [2:0] Curr;
reg [2:0] Next;
reg G_Coke,G_Water,Change1,Change05;
always @(posedge Clk,posedge Req)
	if( Req ) begin
		Curr <= 3'b000;
		Change05 <= 0;
		Change1 <= 0;
		G_Coke <= 0;
		G_Water <= 0;
	end else begin
		Curr <= Next;
	end
always @(OneTL or HalfTL or Coke or Water or Curr)begin
	Next <= 3'b000;
	case (Curr)
	3'b000:begin
		if(OneTL)begin
			Next <= 3'b001;
		end
		else if (HalfTL)begin
			Next <= 3'b010;
		end
	end
	3'b001:begin
		if(OneTL)begin
			Next <= 3'b100;
		end
		else if (HalfTL)begin
			Next <= 3'b011;
		end
	end
	3'b010:begin
		if(OneTL)begin
			Next <= 3'b011;
		end
		else if (HalfTL)begin
			Next <= 3'b001;
		end
	end
	3'b011:begin
		if(OneTL)begin
			Next <= 3'b110;
		end
		else if (HalfTL)begin
			Next <= 3'b100;
		end
		else if (Water)begin
			Next <= 3'b000;
			G_Water <= 1;
		end
	end
	3'b100:begin
		if(OneTL)begin
			Next <= 3'b110;
		end
		else if (HalfTL)begin
			Next <= 3'b101;
		end
		else if (Water)begin
			Next <= 3'b000;
			G_Water <= 1;
			Change05 <= 1;
		end
	end
	3'b101:begin
		if (HalfTL)begin
			Next <= 3'b110;
		end
		else if(Water)begin
			Next <= 3'b000;
			G_Water <= 1;
			Change1 <= 1;
		end
		else if(Coke)begin
			Next <= 3'b000;
			G_Coke <= 1;
		end
	end
	3'b110:begin
		if(Water)begin
			Next <= 3'b000;
			G_Water <= 1;
			Change1 <= 1;
			Change05 <= 1;
		end
		else if(Coke)begin
			Next <= 3'b000;
			G_Coke <= 1;
			Change05 <= 1;
		end
	end
	default:begin
		Next <= Next;
	end
	endcase
end
endmodule
