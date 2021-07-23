package slot;

import java.util.Random;

public class SlotMachine {
	final static int DEFAULT_MONEY=1000;
	final static int PLAYER_LOSE=0;
	final static int PLAYER_WIN1=1;
	final static int PLAYER_WIN2=2;
	final static int PLAYER_WIN3=3;
	final static int PLAYER_WIN4=4;
	
	public static void main(String[] args) {
		run_game();
	}

/***************************************************************
메소드 명 :  run_game()
기능설명 : 슬롯 머신 게임의 주 루틴으로 베팅할 돈을 입력 받은 후,
슬롯 머신의 결과에 따라 현재 돈을 결정합니다.

**************************************************************/
public static void run_game() {
	int current_money=DEFAULT_MONEY;
	int bet_money=0;
	int machine_result;
	display_rule();
	do{
		System.out.printf("현재 갖고있는 돈 : %d\n",current_money);
		bet_money=betting(current_money);
		current_money-=bet_money;
		System.out.printf("현재 금액 : %d\n",current_money);
		System.out.printf("베팅한 금액:%d\n",bet_money);
		//슬로머신을 수행
		 machine_result=run_machine();
		//새로 갖게 되는 금액을 계산한다
		current_money=calculate_money(machine_result,current_money,bet_money);
	}while(current_money>0);
	System.out.println("남은 금액이 없습니다");
}

/***************************************************************
메소드  명 :  betting()
기능설명 : 사용자에게 베팅할 돈을 입력받습니다.
입    력 :
* current_money - 현재 사용자가 갖고 있는 돈
반    환 : [int] - 사용자가 베팅한 돈
**************************************************************/
public static int betting(int current_money) {
	int bet_money=0;
	boolean flag=false;
	do {
		System.out.print("베팅할 돈 입력하세요(종료:0입력):");
		bet_money=DataType.inValue();
		if(bet_money>current_money)
			System.out.println("갖고있는 금액이 부족합니다.");
		else if(bet_money<0)
			System.out.println("베팅할 금액은 0보다 커야 합니다 ");
		else if(bet_money==0) {
			System.out.println("0을 입력시 프로그램 종료합니다. ");
			System.exit(0);
		}else
			flag=true;
	}while(flag!=true);
	return bet_money;
}
/***************************************************************
메소드  명 :  run_machine()
기능설명 : 슬롯 머신을 실행하여 슬롯 머신에 표시되는 기호에 따라
사용자가 돈을 얻었는지 잃었는지 결과를 리턴합니다.
입    력 :
반    환 : [int] - 슬롯 머신의 결과
**************************************************************/
public static int run_machine() {
	char symbol1,symbol2,symbol3;
	int machine_result;
	symbol1=generate_sysmol();
	symbol2=generate_sysmol();
	symbol3=generate_sysmol();
	
	disply_machine(symbol1,symbol2,symbol3);
	System.out.println();
	//슬로머신 기호에 따라 결과를 지정한다
	if(symbol1=='7' && symbol2=='7' && symbol3=='7')
		machine_result=PLAYER_WIN1;//현재금액=현재금액+베팅금액*50
	else if(symbol1=='*' && symbol2=='*' && symbol3=='*')
		machine_result=PLAYER_WIN2;
	else if(symbol1=='@' && symbol2=='@' && symbol3=='@')
		machine_result=PLAYER_WIN3;
	else if((symbol1=='!' && symbol2=='!')
				||(symbol1=='!' && symbol3=='!')
				||(symbol2=='!' && symbol3=='!'))
		machine_result=PLAYER_WIN4;
	else
		machine_result=PLAYER_LOSE;
		
	return machine_result;
}
/***************************************************************
메소드  명 :  generate_symbol()
기능설명 : 난수를 발생하여 슬롯 머신에 표시되는 기호를 생성합니다.
입    력 :
반    환 : [char] - 생성된 슬롯 머신의 기호
**************************************************************/
public static char generate_sysmol() {
	Random random=new Random();
	char[] symbols= {'7','#','@','*','!'};
	int rand_number;
	char new_symbol;
	rand_number=(random.nextInt(5)+1)%5;
	new_symbol=symbols[rand_number];//symbols[0]symbols[1]

	return new_symbol;
}
/***************************************************************
메소드  명 :  display_machine()
기능설명 : 슬롯 머신의 기호를 화면에 표시합니다.
입    력 :
* symbol1 - 기호1  symbol2 - 기호2  symbol3 - 기호3
반    환 : [void]
 * @param symbol3 
 * @param symbol2 
 * @param symbol1 
**************************************************************/
public static void disply_machine(char symbol1, char symbol2, char symbol3) {
	System.out.printf("%c     %c     %c\n",symbol1,symbol2,symbol3);	
}
/***************************************************************
메소드  명 :  calculate_money()
기능설명 : 슬롯 머신의 결과에 따라서 사용자가 갖게 되는 돈을 다시 계산하여 리턴합니다.
입    력 :
* machine_result - 슬롯 머신의 결과
* current_money - 현재 사용자가 갖고 있는 돈
* bet_money - 베팅한 돈
반    환 : [int] - 사용자가 갖게 될 돈
 * @param bet_money 
 * @param current_money 
 * @param machine_result 
**************************************************************/
public static int calculate_money(int machine_result, int current_money, int bet_money) {
	int result_money=0;
	switch (machine_result) {
	case PLAYER_WIN1:
		System.out.println("축하합니다!!! 베팅한 금액의 50배를 얻게 됩니다");
		result_money=current_money+bet_money*50;
		break;
	case PLAYER_WIN2:
		System.out.println("축하합니다!!! 베팅한 금액의 10배를 얻게 됩니다");
		result_money=current_money+bet_money*10;
		break;
	case PLAYER_WIN3:
		System.out.println("축하합니다!!! 베팅한 금액의 5배를 얻게 됩니다");
		result_money=current_money+bet_money*5;
		break;
	case PLAYER_WIN4:
		System.out.println("축하합니다!!! 베팅한 금액의 3배를 얻게 됩니다");
		result_money=current_money+bet_money*3;
		break;
	case PLAYER_LOSE:
		System.out.println("베팅한 금액을 잃었습니다");
		result_money=current_money;
		break;
	}
	return result_money;
}

/***************************************************************
메소드  명 :  display_rule()
기능설명 : 슬롯 머신 게임의 규칙을 화면에 표시합니다.
입    력 : [void]
반    환 : [void]
**************************************************************/
public static void display_rule()
{
	System.out.println(" 7   7   7\tX 50");
	System.out.println(" *   *   *\tX 10");
	System.out.println(" @   @   @\tX 5");
	System.out.println(" !   !  ANY\tX 3");
	System.out.println(" !  ANY  ! \tX 3");
	System.out.println("ANY  !   ! \tX 3");
}
}
