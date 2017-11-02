#include "Repository.h"

#include <fstream>
#include <iostream>
#include <sstream>

Matrix<double> readMatrixFromFile(string file)
{
	Matrix<double> matrix;
	matrix.setName(file);
	string line;

	ifstream myfile(matrix.getInFile());
	if (myfile.is_open())
	{
		while (getline(myfile, line))
		{
			string buf; // Have a buffer string
			stringstream ss(line); // Insert the string into a stream

			vector<string> tokens; // Create vector to hold our words

			while (ss >> buf)
				tokens.push_back(buf);

			vector<double> a;
			for (int i = 0; i < tokens.size(); i++)
			{
				a.push_back(atof(tokens.at(i).c_str()));
			}
			matrix.getContent().push_back(a);
		}
		matrix.setSize();
		myfile.close();
	}
	return matrix;
}

void writeToMatrix(Matrix<double>& matrix)
{
	ofstream myfile(matrix.getOutFile());
	if (myfile.is_open())
	{
		for (int i = 0; i < matrix.getLine(); i++)
		{
			for (int j = 0; j < matrix.getCol(); j++)
			{
				myfile << matrix.getContent().at(i).at(j) << " ";
			}
			myfile << "\n";
		}
		myfile.close();
	}
}

Matrix<ComplexNumber> readCNMatrixFromFile(string file)
{
	Matrix<ComplexNumber> matrix;
	matrix.setName(file);
	string line;

	ifstream myfile(matrix.getInFile());
	if (myfile.is_open())
	{
		while (getline(myfile, line))
		{
			string buf; // Have a buffer string
			stringstream ss(line); // Insert the string into a stream

			vector<string> tokens; // Create vector to hold our words

			while (ss >> buf)
				tokens.push_back(buf);

			vector<ComplexNumber> a;
			for (int i = 0; i < tokens.size(); i++)
			{
				a.push_back(ComplexNumber(tokens.at(i)));
			}
			matrix.getContent().push_back(a);
		}
		matrix.setSize();
		myfile.close();
	}
	return matrix;
}

void writeToCNMatrix(Matrix<ComplexNumber>& matrix)
{
	ofstream myfile(matrix.getOutFile());
	if (myfile.is_open())
	{
		for (int i = 0; i < matrix.getLine(); i++)
		{
			for (int j = 0; j < matrix.getCol(); j++)
			{
				myfile << matrix.getContent().at(i).at(j) << " ";
			}
			myfile << "\n";
		}
		myfile.close();
	}
}
