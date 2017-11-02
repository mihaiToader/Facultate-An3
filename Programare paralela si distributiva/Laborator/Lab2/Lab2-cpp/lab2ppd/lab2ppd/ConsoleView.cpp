#include "ConsoleView.h"
#include <string>
#include <iostream>

#include "Matrix.h"
#include "Repository.h"
#include "Service.h"
#include "ComplexNumber.h"

using namespace std;
void start()
{
	string cmd, matrix1, matrix2;
	int nrThread;
	while (1) {
		cout << "--->";
		cin >> cmd;
		if (cmd == "exit") {
			break;
		}
		else if (cmd.find("dmm") != string::npos) {
			cin >> nrThread;
			cin >> matrix1;
			cin >> matrix2;
			multiplyDoubleMatrix(nrThread, matrix1, matrix2);
		}
		else if (cmd.find("cmm") != string::npos) {
			cin >> nrThread;
			cin >> matrix1;
			cin >> matrix2;
			multiplyComplexMatrix(nrThread, matrix1, matrix2);
		}
		else if (cmd.find("dmw") != string::npos) {
			cin >> nrThread;
			cin >> matrix1;
			cin >> matrix2;
			weirdDoubleMatrix(nrThread, matrix1, matrix2);
		}
		else if (cmd.find("cmw") != string::npos) {
			cin >> nrThread;
			cin >> matrix1;
			cin >> matrix2;
			weirdComplexMatrix(nrThread, matrix1, matrix2);
		}
	}

}
