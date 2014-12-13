package com.example.tet;

public class Balok{
	public int [][] bentuk = new int [4][4];
	public int id;
	public Balok(int id){
//		1=S
//		2=Z
//		3=L
//		4=I
//		5=T
//		6=O
//		7=J
		this.id=id;
		if(id==1){
			bentuk [0][1]= 1;
			bentuk [0][2]= 1;
			bentuk [1][0]= 1;
			bentuk [1][1]= 1;
		}
		else if(id==2){
			bentuk [0][0]= 2;
			bentuk [0][1]= 2;
			bentuk [1][1]= 2;
			bentuk [1][2]= 2;
		}
		else if(id==3){
			bentuk [0][0]= 3;
			bentuk [1][0]= 3;
			bentuk [2][0]= 3;
			bentuk [2][1]= 3;
		}
		else if(id==4){
			bentuk [0][0]= 4;
			bentuk [1][0]= 4;
			bentuk [2][0]= 4;
			bentuk [3][0]= 4;
		}
		else if(id==5){
			bentuk [0][1]= 5;
			bentuk [1][0]= 5;
			bentuk [1][1]= 5;
			bentuk [1][2]= 5;
		}
		else if(id==6){
			bentuk [0][0]= 6;
			bentuk [0][1]= 6;
			bentuk [1][0]= 6;
			bentuk [1][1]= 6;
		}
		else if(id==7){
			bentuk [0][1]= 7;
			bentuk [1][1]= 7;
			bentuk [2][0]= 7;
			bentuk [2][1]= 7;
		}
	}
}