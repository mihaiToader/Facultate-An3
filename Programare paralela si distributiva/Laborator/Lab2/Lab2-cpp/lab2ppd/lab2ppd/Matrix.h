#pragma once
#include <string>
#include <vector>

using namespace std;

template <class T>
class Matrix
{
private:
	int colNr;
	int lineNr;
	vector<vector<T>> content;
	string inFile;
	string outFile;
	string name;
public:
	Matrix() {}
	Matrix(int line, int col);
	vector<vector<T>> &getContent() { return content; }
	void setName(string name);
	string getInFile() { return inFile; }
	string getOutFile() { return outFile; }
	void setSize() {
		lineNr = content.size();
		colNr = content.at(0).size();
	}
	int getLine() { return lineNr; }
	int getCol() { return colNr; }
	string getName() { return name;  }
};

template <class T>
Matrix<T>::Matrix(int line, int col)
{
	lineNr = line;
	colNr = col;
	for (int i = 0; i < line; i++)
	{
		vector<T> a(colNr);
		content.push_back(a);
	}
}



template<class T>
void Matrix<T>::setName(string name)
{
	this->inFile = "C:\\Users\\toade\\Desktop\\Facultate\\Programare paralela si distributiva\\Laborator\\Lab2\\Lab2-java\\matrici\\in\\" + name + ".in";
	this->outFile = "C:\\Users\\toade\\Desktop\\Facultate\\Programare paralela si distributiva\\Laborator\\Lab2\\Lab2-java\\matrici\\out\\" + name + ".out";
	this->name = name;
}