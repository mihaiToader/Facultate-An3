#include "ComplexNumber.h"

ComplexNumber::ComplexNumber(string cmN)
{
	std::istringstream ss(cmN);
	std::string token;

	std::getline(ss, token, ';');
	a = std::stod(token);
	
	std::getline(ss, token, ';');
	b = std::stod(token);
}

ostream & operator<<(ostream & osObject, ComplexNumber &cn)
{
	osObject << cn.getA() << ";" << cn.getB();
	return osObject;
}

ComplexNumber operator/(int num, ComplexNumber & right)
{
	return ComplexNumber(num / right.getA(), num / right.getB());
}

ComplexNumber operator+(ComplexNumber & left, ComplexNumber & right)
{
	return ComplexNumber(left.getA() + right.getA(), left.getB() + right.getB());
}
