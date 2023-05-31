class Solution {
    public int[] solution(int[] numbers, int num1, int num2) {
        int[] answer = {};
        for(int i=num1;i<num2;i++)
        {
        	answer[i]=numbers[i];
        }
        return answer;
    }
}


public class 코테 {
	public static void main(String[] args) {
		Solution s=new Solution();
	//	s.solution(null, 0, 0)
	}
}
