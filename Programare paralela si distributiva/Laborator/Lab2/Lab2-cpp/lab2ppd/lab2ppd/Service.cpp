#include "Service.h"
#include "Matrix.h"
#include "Repository.h"
#include <iostream>
#include <thread>
#include <vector>
#include <string>
#include <chrono>
#include <ctime>
#include <functional>
#include "ComplexNumber.h"

using namespace std;

template<class T> void threadFunction(int start, int end, Matrix<T> a, Matrix<T> b, Matrix<T> &c, function<T (T, T)> func)
{
	int m = a.getCol();
	int n = a.getLine();
	int i = start / m;
	int j = start % m;
	for (int k = start; k < end; k++) {
		if (j >= m) {
			j = 0;
			i++;
		}
		if (i >= n) {
			cout << "Something went wrong!\n";
			return;
		}
		c.getContent().at(i).at(j) = func(a.getContent().at(i).at(j), b.getContent().at(i).at(j));
		j++;
	}
}

template<class T> void makeThreads(int nrThread, Matrix<T> &a, Matrix<T> &b, Matrix<T> &c, function<T(T, T)> func, string operation)
{
	vector<thread> threads;
	vector<int> threadLoad;
	int n = a.getLine();
	int m = a.getCol();
	int size = n * m;
	int rest = size % nrThread;
	int capacity = size / nrThread;
	int start = 0;
	int end = capacity;

	auto startTime = std::chrono::system_clock::now();

	for (int i = 0; i < nrThread; i++) {
		if (rest != 0) {
			rest--;
			end++;
		}
		threads.push_back(thread(threadFunction<T>, start, end, a, b, std::ref(c), func));
		threadLoad.push_back(end - start);
		start = end;
		end += capacity;
	}

	for (int i = 0; i < threads.size(); i++) {
		threads.at(i).join();
	}

	auto endTime = std::chrono::system_clock::now();
	std::chrono::duration<double> elapsed_seconds = endTime - startTime;
	std::chrono::milliseconds d = std::chrono::duration_cast<std::chrono::milliseconds>(elapsed_seconds);

	std::time_t end_time = std::chrono::system_clock::to_time_t(endTime);
	std::time_t start_time = std::chrono::system_clock::to_time_t(startTime);

	std::cout << "Operation: " << operation << "\n\n";
	std::cout << "Started at " << std::ctime(&start_time) << "\n";
	std::cout << "Finished at " << std::ctime(&end_time)
		<< "elapsed time: " << elapsed_seconds.count() << "s\n";
	std::cout << d.count() << " miliseconds\n\n\n";

	for (int i = 0; i < threadLoad.size(); i++)
	{
		cout << "Thread " << i + 1 << " is doing " << threadLoad.at(i) << " operations!\n";
	}
}

void multiplyDoubleMatrix(int nrThread, string matr1, string matr2)
{
	Matrix<double> a = readMatrixFromFile(matr1);
	Matrix<double> b = readMatrixFromFile(matr2);
	Matrix<double> c(a.getLine(), a.getCol());
	c.setName(a.getName() + "_" + b.getName());
	function<double(double, double)> func = [](double d1, double d2) {return d1 * d2; };
	makeThreads<double>(nrThread, a, b, c, func, "Multiply double matrix");
	writeToMatrix(c);
}

void multiplyComplexMatrix(int nrThread, string matr1, string matr2)
{
	Matrix<ComplexNumber> a = readCNMatrixFromFile(matr1);
	Matrix<ComplexNumber> b = readCNMatrixFromFile(matr2);
	Matrix<ComplexNumber> c(a.getLine(), a.getCol());
	c.setName(a.getName() + "_" + b.getName());
	function<ComplexNumber(ComplexNumber, ComplexNumber)> func = [](ComplexNumber d1, ComplexNumber d2) {return d1 * d2; };
	makeThreads<ComplexNumber>(nrThread, a, b, c, func, "Multiply complex matrix");
	writeToCNMatrix(c);
}

void weirdDoubleMatrix(int nrThread, string matr1, string matr2)
{
	Matrix<double> a = readMatrixFromFile(matr1);
	Matrix<double> b = readMatrixFromFile(matr2);
	Matrix<double> c(a.getLine(), a.getCol());
	c.setName(a.getName() + "_" + b.getName());
	function<double(double, double)> func = [](double d1, double d2) {return 1 / ( 1 / d1 + 1 / d2); };
	makeThreads<double>(nrThread, a, b, c, func, "Weird double matrix");
	writeToMatrix(c);
}

void weirdComplexMatrix(int nrThread, string matr1, string matr2)
{
	Matrix<ComplexNumber> a = readCNMatrixFromFile(matr1);
	Matrix<ComplexNumber> b = readCNMatrixFromFile(matr2);
	Matrix<ComplexNumber> c(a.getLine(), a.getCol());
	c.setName(a.getName() + "_" + b.getName());
	function<ComplexNumber(ComplexNumber, ComplexNumber)> func = [](ComplexNumber n1, ComplexNumber n2) {return ComplexNumber(((n1.getA() + n2.getA())*(n1.getA() * n2.getA() - n1.getB()*n2.getB()) +
		(n1.getB() + n2.getB())*(n1.getA()*n2.getB() + n2.getA()*n1.getB())) /
		(pow(n1.getA() + n2.getA(), 2) - pow(n1.getB() + n2.getB(), 2)),
		((n1.getA() + n2.getA())*(n1.getA()*n2.getB() + n2.getA()*n1.getB())
			+ (n1.getB() + n2.getB())*(-n1.getA() * n2.getA() + n1.getB()*n2.getB())) /
			(pow(n1.getA() + n2.getA(), 2) - pow(n1.getB() + n2.getB(), 2)));  };
	makeThreads<ComplexNumber>(nrThread, a, b, c, func, "Multiply complex matrix");
	writeToCNMatrix(c);
}


