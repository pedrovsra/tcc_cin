package com.pedro.auxiliary;

import com.pedro.entities.PitchClassProfile;

public class MeuViterbi {

	private double[][] T1;
	private double[][] T2;
	//private int N; // tamanho do conjunto de observacoes
	private int K; // tamanho do conjunto de estados
	private int T; // tamanho da sequencia de obersevacoes
	
	private double[][] A;
	private double[][] B;
	
	private class Node {
		private double prob;
		private int prev;
		
		public Node() {
			this.prob = 0;
			this.prev = -1;
		}
		
		public Node(double prob, int prev) {
			this.prob = prob;
			this.prev = prev;
		}
		
		public Node(double prob) {
			this.prob = prob;
			this.prev = -1;
		}

		public double getProb() {
			return prob;
		}

		public void setProb(double prob) {
			this.prob = prob;
		}

		public int getPrev() {
			return prev;
		}

		public void setPrev(int prev) {
			this.prev = prev;
		}
	}
	
	private double maxk(int i, int j) {
		double max = 0, sum;
		int kmax = -1;
		
		for(int k = 0; k < this.T1.length; k++) {
			sum = this.T1[k][i-1] * this.A[k][j] * this.B[j][i];
			if(sum > max) {
				max = sum;
				kmax = k;
			}
		}
		return kmax;
	}
	
	private double argmaxk(int i, int j) {
		double max = 0, sum;
		int kmax = -1;
		
		for(int k = 0; k < this.T1.length; k++) {
			sum = this.T1[k][i-1] * this.A[k][j];
			if(sum > max) {
				max = sum;
				kmax = k;
			}
		}
		return kmax;
	}
	
	/**
	 * @param PCPs sequencia de observacoes Y = (y1, y2, ..., yT) tal que yT == i se a observacao no tempo t é oi
	 * @param estados S = (s1, s2, ..., sK)
	 * @param matriz_transicao A de tamanho K*K tal que A[i,j] é a probabilidade de transicao do estado si para o estado sj
	 * @param matriz_emissao B de tamanho K*N tal que B[i,j] é a probabilidade de observar oj a partir do estado si
	 * @param probs_iniciais PI de tamanho K tal que PIi é a probabilidade de x1 = si
	 */
	public void run(PitchClassProfile[] PCPs, String[] estados, double[][] matriz_transicao, double[][] matriz_emissao, double[] probs_iniciais) {
		this.K = estados.length;
		this.T = PCPs.length;
		
		this.A = matriz_transicao;
		this.B = matriz_emissao;
		
		this.T1 = new double[this.K][this.T];
		this.T2 = new double[this.K][this.T];
		
		//Node[][] V = new Node[T][K];
		//double max_tr_prob, max_prob;
		
		for (int i = 0; i < this.K; i++) { // para cada estado Si
			//V[0][st] = new Node(probs_iniciais[st] * matriz_emissao[st][0]);
			
			this.T1[i][0] = probs_iniciais[i] * matriz_emissao[i][0]; // T1[i,1] <- PI[i] * B[i, y1]
			this.T2[i][0] = 0; // T2[i,1] <- 0
		}
		
		for(int i = 1; i < this.T; i++) { // para i = 2 ate T
			for(int j = 0; j < this.K; j++) { // para cada estado sj
				
//				max_tr_prob = argmax(V, matriz_transicao, t, st, this.K);
//				
//				for(int prev_st = 0; prev_st < this.K; prev_st++) {
//					if(V[t-1][prev_st].getProb() * matriz_transicao[prev_st][st] == max_tr_prob) {
//						max_prob = max_tr_prob * matriz_emissao[st][t];
//						V[t][st] = new Node(max_prob, prev_st);
//						break;
//					}
//				}
				
				this.T1[i][j] = maxk(i, j); // T1[i,j] <- maxk (T1[k, i-1] * A[k,j] * B[j, yi]
				this.T2[i][j] = argmaxk(i, j); // T2[i,j] <- argmaxk(T1[k, i-1] * A[k,j])
			}
		}
//		max_prob = 0;
//		int previous = -1;
//		for(int q = 0; q < V[V.length-1].length; q++) {
//			max_prob = Math.max(max_prob, V[V.length-1][q].getProb());
//		}
		// z[T] <- argmaxk(T1[k, T])
		// x[T] <- s(zT)
		
		
			
		
		for(int i = this.T - 1; i > 0; i--) {
			//z[i-1] <- T2[zi, i]
			//x[i-1] <- S[z[i-1]]
		}
		// return X
	}
}