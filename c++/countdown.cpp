#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <vector>
#include <memory>
#include <string>
#include <map>
#include <limits>
#include <algorithm>

using std::cout;
using std::cin;
using std::endl;
using std::cerr;
using std::ostream;



class Result{

private:
	std::shared_ptr<Result> value_1 ;
	std::shared_ptr<Result> value_2 ;
	//
	long value;
	char operator_;

public:
	Result() {
		value = -1;
	}


	Result(long value_){
		this->value = value_;
	}

	Result(std::shared_ptr<Result> r1, std::shared_ptr<Result> r2, char operator_){
		this->value = -1;
		if(operator_ == '+') {
			this->value = r1->value + r2->value;
		}
		
		if(operator_ == '*') {
			this->value = r1->value * r2->value;
		}

		if(operator_ == '-' && r1->value >= r2->value) {
			this->value = r1->value - r2->value;
		}

		if(operator_ == '/' && (r2->value > 0) && ((r1->value % r2->value) == 0)) {
			this->value = (r1->value / r2->value);
		}

		this->value_1 = r1;
		this->value_2 = r2;
		this->operator_ = operator_;
	}

	~Result(){
		//cout<<"Destroy"<<endl;
	}
	bool isTarget(long target){
		return target == this->value;
	}

	long getValue(){
		return this->value;
	}

	friend ostream& operator<<(ostream& os, const Result& res) ;
	
	friend bool operator< (const Result& lhs, const Result& rhs);

};


bool operator< (const Result& lhs, const Result& rhs){ /* do actual comparison */ 
	return lhs.value < rhs.value;
}

ostream& operator<<(ostream& os, const Result& res)  
	{  
		//std:://cerr<<"[1]::"<< res.value_1 << "==> "<< res.value <<endl;
		if(res.value_1){
			//std:://cerr<<"[2]"<<endl;
			os<< (*(res.value_1));
		}	

		if(res.value_2 ){
			//std:://cerr<<"[3]"<<endl;
			os<< (*(res.value_2));
		}

		if(res.value != -1 && res.value_2  && res.value_1 ){
			//std:://cerr<<"[4]"<<endl;
    		os<< (res.value_1->value)<<" " <<res.operator_<<" "<< (res.value_2->value) <<" = "<< res.value<<endl;
    	} else{
    		//std:://cerr<<"[5]"<<endl;
    		os<<"";
    	}

    	return os;  
	}

int
read_int() {
    int v;
    int r = scanf("%d", &v);

    /*printf("value read (int): %d:%d\n", r,v); //*/
    if (r <= 0) {
        exit(0);
    }
    return v;
}

long
read_long() {
    long v;
    int r = scanf("%ld", &v);

    /*printf("value read (int): %d:%d\n", r,v); //*/
    if (r <= 0) {
        exit(0);
    }
    return v;
}

std::shared_ptr<Result> best_solution;
long current_diff = std::numeric_limits<long>::infinity();

char ope_list[] = {'+','-','*','/'};

std::map<std::string, std::string> visited_states;

bool myt_lt (const std::shared_ptr<Result>& left, const std::shared_ptr<Result>& right){
	return (*left.get() < *right.get());
}


void search(std::vector<std::shared_ptr<Result> > results, long target){
	for (int i = 0; i < results.size(); i++) {
        if(results[i]->isTarget(target)){
        	best_solution = results[i];
        	return;
        }
	}
	if(results.size()==1){
		return;
	}
	if(current_diff == 0){
		return;
	}
	std::sort(results.begin(), results.end());

	std::sort(
		results.begin(), 
		results.end(),
    	myt_lt);

	std::string key = "";
	for (int i = 0; i < results.size(); i++) {
		key += std::to_string((results[i]->getValue())) + ":";
	}
	//cout<<"key : "<<key<<endl; 
	if(visited_states.find(key) != visited_states.end()){
		//cout<<"visited ... "<<endl;
		return;
	}
	visited_states[key] = key;
	//
	//cerr<<"Results :: "<<results.size() << " current_diff: "<<current_diff<< "key: ["<<key<<"]"<<endl;
	for (int i = 0; i < results.size(); i++) {
        std::shared_ptr<Result> current = results[i];
        std::vector<std::shared_ptr<Result> > others;
        for (int j = 0; j < results.size(); j++) {
            if (i != j) {
                others.push_back(results[j]);
            }
        }
        //cerr<<" 1 [" << others.size() << "]"<< endl;
        std::vector<std::shared_ptr<Result> > calculations;
        for(int k=0; k<others.size(); k++) {
        	calculations.clear();
        	//cerr<<" 1.1 "<<endl;
        	std::vector<std::vector<std::shared_ptr<Result> > > newResults;
        	//cerr<<" 1.2 "<<endl;
        	for(int ope=0; ope<4; ope++){
        		//cerr<<" 1.3 ope[" << ope << "] [" << ope_list[ope] <<"] k [" << k <<"]" <<endl;
        		//cerr<< " others_k "<< others[k] <<endl;
        		std::shared_ptr<Result> rcalc(new Result(others[k], current, ope_list[ope]));
        		//cerr<<" 1.4 "<<endl;
        		calculations.push_back(rcalc);
        		//
        		//cerr<<" 1.5 "<<endl;
        		std::vector<std::shared_ptr<Result> > newResultsAux;
        		//cerr<<" 1.6 "<<endl;
        		newResults.push_back(newResultsAux);
        		//cerr<<" 1.7 "<<endl;
        	}
        	//
        	//cerr<<" 2 "<<endl;
        	//
        	for(int ope=0;ope<4;ope++){
	        	for (int j = 0; j < others.size(); j++) {
	        		if(k != j) {
	        			newResults[ope].push_back(others[j]);	
	        		}
	        	}
        	}
        	//cerr<<" 3 "<<endl;
        	//
        	for(int ope=0;ope<4;ope++){
	        	if(calculations[ope]->isTarget(target)){
	        		best_solution = calculations[ope];
	        		current_diff = 0;
	        		return;	
	        	}


	        	if(current_diff > (abs(target - calculations[ope]->getValue() ) ) ) {
	        		current_diff = abs(target - calculations[ope]->getValue());	
	        		best_solution = calculations[ope];
	        	}
	      	}
	        
	        for(int ope=0;ope<4;ope++){
	        	
	        	if(calculations[ope]->getValue() > 0){
	        		newResults[ope].push_back(calculations[ope]);
	        		
	        		//*/
	        		search(newResults[ope], target);
	        	}	
	        }
	        //cerr<<" 4 "<<endl;
        }

    }
    
    
	
}

int main(int argc, char **argv) {

	int cases = read_int();
	int count =0;
	while (true && count < cases) {
        if (std::cin.eof()) {
            break;
        }   
		//
		current_diff = std::numeric_limits<long>::max();
		best_solution.reset();
		visited_states.clear();
		//
		long a[6];
		std::vector<std::shared_ptr<Result> > results;
		for(int i=0;i<6;i++) {
			a[i] = read_long();	
			std::shared_ptr<Result> r( new Result(a[i]));
			//cout<<r<<endl;
			results.push_back(r);
		}
		long target = read_long();
		//
		//cout<<"Call search"<<endl;
		search(results, target);
		std::shared_ptr<Result> r3( new Result(results[0] , results[5], '+'));
		//cout<<(*r3)<<endl;
		std::shared_ptr<Result>  r4 (new Result(r3 , results[5], '+'));
		//cout<<"r4 val:: "<<r4<<endl;
		std::shared_ptr<Result>  r5 (new Result(r3 , r4, '*'));
		//cout<<"r5 val:: "<<(*r5)<<endl;
		
		// 75 50 2 3 8 7
		//cout<<"UPD current_diff:::"<<current_diff<<endl;
		//
		cout<<"Target: " << target<<endl;
    	if(best_solution){
    		cout<<(*best_solution);	
    		cout<<"Best approx: "<<best_solution->getValue()<<endl;
    	}
    	cout<<endl;
		count++;
	}

	//
	return 0;
}