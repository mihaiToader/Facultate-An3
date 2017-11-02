#pragma once

#include <string>
#include <sstream>
#include <vector>
#include <iostream>  
using namespace std;

class ComplexNumber
{
private:
	double a;
	double b;

public:
	ComplexNumber(double a, double b) : a{ a }, b{ b } {}
	ComplexNumber() {
		a = 0;
		b = 0;
	}
	ComplexNumber(string cmN);

	double getA() { return a; }
	double getB() { return b; }
	void setA() { this->a = a; }
	void setB() { this->b = b; }

	friend ostream& operator<<(ostream& osObject, ComplexNumber& cn);

	ComplexNumber operator* (ComplexNumber& cn)
	{
		return ComplexNumber(a*cn.getA() - b*cn.getB(), a*cn.getB() + cn.getA() *b);
	}

	friend ComplexNumber operator/ (int num, ComplexNumber& right);

	friend ComplexNumber operator+ (ComplexNumber& left, ComplexNumber& right);
};