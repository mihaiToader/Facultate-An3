//
// Created by mtoader on 10/19/2017.
//

#ifndef LAB2_MATRIX_H
#define LAB2_MATRIX_H

#include <vector>
#include <string>
#include <ctime>
#include <cstdlib>
#include "Matrix_element.h"

class Matrix
{
 public:
  Matrix() = default;
  Matrix(std::string name, int lines, int columns, std::vector<std::vector<Matrix_element>> content);
  Matrix(std::string name, int lines, int columns);

  void generate_content();
 private:
  int columnNumber;
  int lineNumber;
  std::vector<std::vector<Matrix_element>> content;
  std::string name;
};

#endif //LAB2_MATRIX_H
