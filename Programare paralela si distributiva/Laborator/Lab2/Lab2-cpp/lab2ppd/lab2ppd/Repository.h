#pragma once

#include "Matrix.h"
#include "ComplexNumber.h"
#include <string>
using namespace std;

Matrix<double> readMatrixFromFile(string file);

void writeToMatrix(Matrix<double> &matrix);

Matrix<ComplexNumber> readCNMatrixFromFile(string file);

void writeToCNMatrix(Matrix<ComplexNumber> &matrix);