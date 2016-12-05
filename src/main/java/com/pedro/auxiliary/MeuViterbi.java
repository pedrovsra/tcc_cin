package com.pedro.auxiliary;

import com.pedro.entities.PitchClassProfile;

public class MeuViterbi {

	private double[][] T1;
	private double[][] T2;
	private double[] Z;
	private String[] X;
	private int K; // tamanho do conjunto de estados
	private int T; // tamanho da sequencia de obersevacoes

	private double[][] A;
	private double[][] B;

	private double arrSum(double[] arr) {
		double sum = 0;

		for (int i = 0; i < arr.length; i++)
			sum += arr[i];

		return sum;
	}

	private void toPercent(int index) {
		double[] aux = new double[this.T1.length];
		double s;

		for(int i = 0; i < this.T1.length; i++) {
			aux[i] = this.T1[i][index];
		}

		s = arrSum(aux);
		for (int j = 0; j < this.T1.length; j++) {
			this.T1[j][index] = (this.T1[j][index] / s);
		}
	}
	
	private double maxk(int i, int j) {
		double max = 0, sum = 0;

		for(int k = 0; k < this.T1.length; k++) {
			sum = this.T1[k][i - 1] * this.A[k][j] * this.B[j][i];
			max = Math.max(sum, max);
		}
		return max;
	}

	private double argmaxk(int i, int j) {
		double max = 0, sum;
		int kmax = -1;

		for(int k = 0; k < this.T1.length; k++) {
			sum = this.T1[k][i - 1] * this.A[k][j];
			if(sum >= max) {
				max = sum;
				kmax = k;
			}
		}
		return kmax;
	}

	private double argmaxZ() {
		double max = 0, sum;
		int kmax = -1;

		for(int k = 0; k < this.T1.length; k++) {
			sum = this.T1[k][this.T - 1];
			if(sum >= max) {
				max = sum;
				kmax = k;
			}
		}
		return kmax;
	}
	
	/**
	 * @param PCPs sequencia de observacoes Y = (y1, y2, ..., yT) tal que yT == i se a observacao no tempo t � oi
	 * @param estados S = (s1, s2, ..., sK)
	 * @param matriz_transicao A de tamanho K*K tal que A[i,j] � a probabilidade de transicao do estado si para o estado sj
	 * @param matriz_emissao B de tamanho K*N tal que B[i,j] � a probabilidade de observar oj a partir do estado si
	 * @param probs_iniciais PI de tamanho K tal que PIi � a probabilidade de x1 = si
	 */
	public void run(PitchClassProfile[] PCPs, String[] estados, double[][] matriz_transicao, double[][] matriz_emissao, double[] probs_iniciais, int estado_final) {
		this.K = estados.length;
		this.T = PCPs.length;
		
		this.A = matriz_transicao;
		this.B = matriz_emissao;
		
		this.T1 = new double[this.K][this.T];
		this.T2 = new double[this.K][this.T];
		
		this.Z = new double[this.T];
		this.X = new String[this.T];
		
		for (int i = 0; i < this.K; i++) { // para cada estado Si
			this.T1[i][0] = probs_iniciais[i] * matriz_emissao[i][0]; // T1[i,1] <- PI[i] * B[i, y1]
			this.T2[i][0] = 0; // T2[i,1] <- 0
		}
		
		for(int i = 1; i < this.T; i++) { // para i = 2 ate T
			toPercent(i-1);
			for(int j = 0; j < this.K; j++) { // para cada estado sj
				this.T1[j][i] = maxk(i, j); // T1[i,j] <- maxk (T1[k, i-1] * A[k,j] * B[j, yi]
				this.T2[j][i] = argmaxk(i, j); // T2[i,j] <- argmaxk(T1[k, i-1] * A[k,j])
			}
		}
		this.Z[this.T - 1] = estado_final;//argmaxZ(); // z[T] <- argmaxk(T1[k, T])
		this.X[this.T - 1] = estados[(int) this.Z[this.T - 1]]; // x[T] <- s(zT)
		
		for(int i = this.T-1; i > 0; i--) {
			this.Z[i - 1] = this.T2[(int) this.Z[i]][i];// z[i-1] <- T2[zi, i]
			this.X[i - 1] = estados[(int) this.Z[i - 1]]; // x[i-1] <- S[z[i-1]]
		}
		
		String last = "";
		for(int i = 0; i < this.T; i++) {
			if(!this.X[i].equals(last)) {
				last = this.X[i];
				System.out.print(this.X[i] + " ");
			}
		}
	}
}