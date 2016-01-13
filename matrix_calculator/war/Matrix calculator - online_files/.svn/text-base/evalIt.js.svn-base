function evalIt(matrixA, matrixB, textarea, formula) {
	var matrix;
	switch (formula) {
		case "A*B" :
			matrix = doMatrMult_AB(matrixA, matrixB);
			break;
		case "B*A" :
			matrix = doMatrMult_AB(matrixB, matrixA);
			break;
		case "A*B*A" :
			matrix = doMatrMult_ABA(matrixA, matrixB);
			break;
		case "B*A*B" :
			matrix = doMatrMult_ABA(matrixB, matrixA);
			break;
		case "A^2" :
			matrix = powMatr(matrixA);
			break;
		case "B^2" :
			matrix = powMatr(matrixB);
			break;
		case "A+B" :
			matrix = doMatrAdd(matrixA, matrixB);
			break;
		case "B+A" :
			matrix = doMatrAdd(matrixB, matrixA);
			break;
		default :
			errorMsg('Unsupported formula.');
	}
	textarea.value = matr2str(matrix);
}

function transposeMatrix(textarea) {
	var parsed = parseMatrix(textarea.value);
	if (parsed) {
		textarea.value = matr2str( getTranspose( parsed ) );
	}
}

