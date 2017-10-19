//
// Created by mtoader on 10/19/2017.
//

#ifndef LAB2_MATRIX_ELEMENT_H
#define LAB2_MATRIX_ELEMENT_H

template <class T>
class Matrix_element
{
 public:
  virtual T generateElement() = 0;
  T getElement() { return element; }
  T setElement(T el) { element = el; }
  virtual T operator+(T el1, const T &el2) = 0;
  virtual T operator-(T el1, const T &el2) = 0;
 private:
  T element;
};

#endif //LAB2_MATRIX_ELEMENT_H
