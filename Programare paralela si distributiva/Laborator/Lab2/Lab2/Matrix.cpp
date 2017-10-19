//
// Created by mtoader on 10/19/2017.
//

#include "Matrix.h"

#include <utility>

Matrix::Matrix(std::string name, int lines, int columns, std::vector<std::vector<Matrix_element>> content)
{
  this->name = std::move(name);
  this->lineNumber = lines;
  this->columnNumber = columns;
  this->content = content;
}

Matrix::Matrix(std::string name, int lines, int columns)
{
  this->name = std::move(name);
  this->lineNumber = lines;
  this->columnNumber = columns;
}

void Matrix::generate_content()
{
  this->content.clear();
  for (int i = 0; i < lineNumber; ++i)
  {
    std::vector<Matrix_element> line;
    for (int j = 0; j < columnNumber; ++j)
    {
      Matrix_element e;
      e.generateElement();
      line.push_back(e);
    }
    this->content.push_back(line);
  }
}
