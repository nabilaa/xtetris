package com.example.tet;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;

public class Board extends Thread {
	ImageView[][] board = new ImageView[18][9];
	int[][] bayangan = new int[18][9];
	int[] X1 = { 0, 0 };
	int[] X2 = { 0, 0 };
	int[] X3 = { 0, 0 };
	int[] X4 = { 0, 0 };
	int score = 0;
	boolean gameOver = false;
	boolean newBalok = false;
	MediaPlayer mp;
	Drawable tileColor;

	public void awal() {
		for (int i = 0; i < 18; i++) {
			for (int j = 0; j < 9; j++) {
				bayangan[i][j] = 0;
			}
		}
		int[] X1 = { 0, 0 };
		int[] X2 = { 0, 0 };
		int[] X3 = { 0, 0 };
		int[] X4 = { 0, 0 };
		int score = 0;
		boolean gameOver = false;
		boolean newBalok = false;
	}

	public void getKoordinat(Balok balok) {
		int[][] bal = balok.bentuk;
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (bal[i][j] > 0) {
					count++;
					if (count == 1) {
						X1[0] = i;
						X1[1] = j + 4;
					} else if (count == 2) {
						X2[0] = i;
						X2[1] = j + 4;
					} else if (count == 3) {
						X3[0] = i;
						X3[1] = j + 4;
					} else if (count == 4) {
						X4[0] = i;
						X4[1] = j + 4;
					}
				}
			}
		}
	}

	public boolean bisaTurun(int[] a, int[] b, int[] c, int[] d) {
		if (Math.max(a[0], Math.max(b[0], Math.max(c[0], d[0]))) + 1 < 18) {
			if (a[1] == b[1]) {
				if (a[1] == c[1]) {
					// semua satu colom
					if (a[1] == d[1]) {
						if (bayangan[Math.max(a[0],
								Math.max(b[0], Math.max(c[0], d[0]))) + 1][a[1]] == 1) {
							return false;
						} else {
							return true;
						}
					} else {
						if (bayangan[Math.max(a[0], Math.max(b[0], c[0])) + 1][a[1]] == 1
								|| bayangan[d[0] + 1][d[1]] == 1) {
							return false;
						} else {
							return true;
						}
					}
				} else {
					if (a[1] == d[1]) {
						if (bayangan[Math.max(a[0], Math.max(b[0], d[0])) + 1][a[1]] == 1
								|| bayangan[c[0] + 1][c[1]] == 1) {
							return false;
						} else {
							return true;
						}
					} else {// c?d
						if (c[1] != d[1]) {
							if (bayangan[Math.max(a[0], b[0]) + 1][a[1]] == 1
									|| bayangan[d[0] + 1][d[1]] == 1
									|| bayangan[c[0] + 1][c[1]] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[Math.max(a[0], b[0]) + 1][a[1]] == 1
									|| bayangan[Math.max(c[0], d[0]) + 1][c[1]] == 1) {
								return false;
							} else {
								return true;
							}
						}
					}
				}
			} else {
				if (a[1] == c[1]) {
					if (a[1] == d[1]) {
						if (bayangan[Math.max(a[0], Math.max(c[0], d[0])) + 1][a[1]] == 1
								|| bayangan[b[0] + 1][b[1]] == 1) {
							return false;
						} else {
							return true;
						}
					} else {
						if (b[1] == d[1]) {
							if (bayangan[Math.max(a[0], c[0]) + 1][a[1]] == 1
									|| bayangan[Math.max(b[0], d[0]) + 1][b[1]] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[Math.max(a[0], c[0]) + 1][a[1]] == 1
									|| bayangan[d[0] + 1][d[1]] == 1
									|| bayangan[b[0] + 1][b[1]] == 1) {
								return false;
							} else {
								return true;
							}
						}
					}
				} else {
					if (a[1] == d[1]) {
						if (b[1] == c[1]) {
							if (bayangan[Math.max(a[0], d[0]) + 1][a[1]] == 1
									|| bayangan[Math.max(b[0], c[0]) + 1][b[1]] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[Math.max(a[0], d[0]) + 1][a[1]] == 1
									|| bayangan[c[0] + 1][c[1]] == 1
									|| bayangan[b[0] + 1][b[1]] == 1) {
								return false;
							} else {
								return true;
							}
						}
					} else {
						if (b[1] == c[1]) {
							if (b[1] == d[1]) {
								if (bayangan[Math.max(b[0],
										Math.max(c[0], d[0])) + 1][b[1]] == 1
										|| bayangan[a[0] + 1][a[1]] == 1) {
									return false;
								} else {
									return true;
								}
							} else {
								if (bayangan[Math.max(b[0], c[0]) + 1][c[1]] == 1
										|| bayangan[a[0] + 1][a[1]] == 1
										|| bayangan[d[0] + 1][d[1]] == 1) {
									return false;
								} else {
									return true;
								}
							}
						} else {
							if (b[1] == d[1]) {
								if (bayangan[Math.max(b[0], d[0]) + 1][b[1]] == 1
										|| bayangan[a[0] + 1][a[1]] == 1
										|| bayangan[c[0] + 1][c[1]] == 1) {
									return false;
								} else {
									return true;
								}
							} else {
								if (c[1] == d[1]) {
									if (bayangan[Math.max(c[0], d[0]) + 1][c[1]] == 1
											|| bayangan[a[0] + 1][a[1]] == 1
											|| bayangan[b[0] + 1][b[1]] == 1) {
										return false;
									} else {
										return true;
									}
								} else {
									if (bayangan[a[0] + 1][a[1]] == 1
											|| bayangan[b[0] + 1][b[1]] == 1
											|| bayangan[d[0] + 1][d[1]] == 1
											|| bayangan[c[0] + 1][c[1]] == 1) {
										return false;
									} else {
										return true;
									}
								}
							}
						}
					}
				}
			}
		} else {
			return false;
		}
	}

	public boolean bisaKekiri(int[] a, int[] b, int[] c, int[] d) {
		if (Math.min(a[1], Math.min(b[1], Math.min(c[1], d[1]))) > 0) {
			if (a[0] == b[0]) {
				if (a[0] == c[0]) {
					// semua satu kolom
					if (a[0] == d[0]) {
						if (bayangan[a[0]][Math.min(a[1],
								Math.min(b[1], Math.min(c[1], d[1]))) - 1] == 1) {
							return false;
						} else {
							return true;
						}
					} else {
						if (bayangan[a[0]][Math.min(a[1], Math.min(b[1], c[1])) - 1] == 1
								|| bayangan[d[0]][d[1] - 1] == 1) {
							return false;
						} else {
							return true;
						}
					}
				} else {
					if (a[0] == d[0]) {
						if (bayangan[a[0]][Math.min(a[1], Math.min(b[1], d[1])) - 1] == 1
								|| bayangan[c[0]][c[1] - 1] == 1) {
							return false;
						} else {
							return true;
						}
					} else {
						if (c[0] != d[0]) {
							if (bayangan[a[0]][Math.min(a[1], b[1]) - 1] == 1
									|| bayangan[d[0]][d[1] - 1] == 1
									|| bayangan[c[0]][c[1] - 1] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[a[0]][Math.min(a[1], b[1]) - 1] == 1
									|| bayangan[c[0]][Math.min(c[1], d[1]) - 1] == 1) {
								return false;
							} else {
								return true;
							}
						}
					}
				}
			} else {
				if (a[0] == c[0]) {
					if (a[0] == d[0]) {
						if (bayangan[a[0]][Math.min(a[1], Math.min(c[1], d[1])) - 1] == 1
								|| bayangan[b[0]][b[1] - 1] == 1) {
							return false;
						} else {
							return true;
						}
					} else {
						if (b[0] == d[0]) {
							if (bayangan[a[0]][Math.min(a[1], c[1]) - 1] == 1
									|| bayangan[b[0]][Math.min(b[1], d[1]) - 1] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[a[0]][Math.min(a[1], c[1]) - 1] == 1
									|| bayangan[d[0]][d[1] - 1] == 1
									|| bayangan[b[0]][b[1] - 1] == 1) {
								return false;
							} else {
								return true;
							}
						}
					}
				} else {
					if (a[0] == d[0]) {
						if (b[0] == c[0]) {
							if (bayangan[a[0]][Math.min(a[1], d[1]) - 1] == 1
									|| bayangan[b[0]][Math.min(b[1], c[1]) - 1] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[a[0]][Math.min(a[1], d[1]) - 1] == 1
									|| bayangan[c[0]][c[1] - 1] == 1
									|| bayangan[b[0]][b[1] - 1] == 1) {
								return false;
							} else {
								return true;
							}
						}
					} else {
						if (b[0] == c[0]) {
							if (b[0] == d[0]) {
								if (bayangan[b[0]][Math.min(b[1],
										Math.min(c[1], d[1])) - 1] == 1
										|| bayangan[a[0]][a[1] - 1] == 1) {
									return false;
								} else {
									return true;
								}
							} else {
								if (bayangan[c[0]][Math.min(b[1], c[1]) - 1] == 1
										|| bayangan[a[0]][a[1] - 1] == 1
										|| bayangan[d[0]][d[1] - 1] == 1) {
									return false;
								} else {
									return true;
								}
							}
						} else {
							if (b[0] == d[0]) {
								if (bayangan[b[0]][Math.min(b[1], d[1]) - 1] == 1
										|| bayangan[a[0]][a[1] - 1] == 1
										|| bayangan[c[0]][c[1] - 1] == 1) {
									return false;
								} else {
									return true;
								}
							} else {
								if (c[0] == d[0]) {
									if (bayangan[c[0]][Math.min(c[1], d[1]) - 1] == 1
											|| bayangan[a[0]][a[1] - 1] == 1
											|| bayangan[b[0]][b[1] - 1] == 1) {
										return false;
									} else {
										return true;
									}
								} else {
									if (bayangan[a[0]][a[1] - 1] == 1
											|| bayangan[b[0]][b[1] - 1] == 1
											|| bayangan[d[0]][d[1] - 1] == 1
											|| bayangan[c[0]][c[1] - 1] == 1) {
										return false;
									} else {
										return true;
									}
								}
							}
						}
					}
				}
			}
		} else {
			return false;
		}
	}

	public boolean bisaKekanan(int[] a, int[] b, int[] c, int[] d) {
		if (Math.max(a[1], Math.max(b[1], Math.max(c[1], d[1]))) < 8) {
			if (a[0] == b[0]) {
				if (a[0] == c[0]) {
					if (a[0] == d[0]) {
						if (bayangan[a[0]][Math.max(a[1],
								Math.max(b[1], Math.max(c[1], d[1]))) + 1] == 1) {
							return false;
						} else {
							return true;
						}
					} else {
						if (bayangan[a[0]][Math.max(a[1], Math.max(b[1], c[1])) + 1] == 1
								|| bayangan[d[0]][d[1] + 1] == 1) {
							return false;
						} else {
							return true;
						}
					}
				} else {
					if (a[0] == d[0]) {
						if (bayangan[a[0]][Math.max(a[1], Math.max(b[1], d[1])) + 1] == 1
								|| bayangan[c[0]][c[1] + 1] == 1) {
							return false;
						} else {
							return true;
						}
					} else {
						if (c[0] != d[0]) {
							if (bayangan[a[0]][Math.max(a[1], b[1]) + 1] == 1
									|| bayangan[d[0]][d[1] + 1] == 1
									|| bayangan[c[0]][c[1] + 1] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[a[0]][Math.max(a[1], b[1]) + 1] == 1
									|| bayangan[c[0]][Math.max(c[1], d[1]) + 1] == 1) {
								return false;
							} else {
								return true;
							}
						}
					}
				}
			} else {
				if (a[0] == c[0]) {
					if (a[0] == d[0]) {
						if (bayangan[a[0]][Math.max(a[1], Math.max(c[1], d[1])) + 1] == 1
								|| bayangan[b[0]][b[1] + 1] == 1) {
							return false;
						} else {
							return true;
						}
					} else {
						if (b[0] == d[0]) {
							if (bayangan[a[0]][Math.max(a[1], c[1]) + 1] == 1
									|| bayangan[b[0]][Math.max(b[1], d[1]) + 1] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[a[0]][Math.max(a[1], c[1]) + 1] == 1
									|| bayangan[d[0]][d[1] + 1] == 1
									|| bayangan[b[0]][b[1] + 1] == 1) {
								return false;
							} else {
								return true;
							}
						}
					}
				} else {
					if (a[0] == d[0]) {
						if (b[0] == c[0]) {
							if (bayangan[a[0]][Math.max(a[1], d[1]) + 1] == 1
									|| bayangan[b[0]][Math.max(b[1], c[1]) + 1] == 1) {
								return false;
							} else {
								return true;
							}
						} else {
							if (bayangan[a[0]][Math.max(a[1], d[1]) + 1] == 1
									|| bayangan[c[0]][c[1] + 1] == 1
									|| bayangan[b[0]][b[1] + 1] == 1) {
								return false;
							} else {
								return true;
							}
						}
					} else {
						if (b[0] == c[0]) {
							if (b[0] == d[0]) {
								if (bayangan[b[0]][Math.max(b[1],
										Math.max(c[1], d[1])) + 1] == 1
										|| bayangan[a[0]][a[1] + 1] == 1) {
									return false;
								} else {
									return true;
								}
							} else {
								if (bayangan[c[0]][Math.max(b[1], c[1]) + 1] == 1
										|| bayangan[a[0]][a[1] + 1] == 1
										|| bayangan[d[0]][d[1] + 1] == 1) {
									return false;
								} else {
									return true;
								}
							}
						} else {
							if (b[0] == d[0]) {
								if (bayangan[b[0]][Math.max(b[1], d[1]) + 1] == 1
										|| bayangan[a[0]][a[1] + 1] == 1
										|| bayangan[c[0]][c[1] + 1] == 1) {
									return false;
								} else {
									return true;
								}
							} else {
								if (c[0] == d[0]) {
									if (bayangan[c[0]][Math.max(c[1], d[1]) + 1] == 1
											|| bayangan[a[0]][a[1] + 1] == 1
											|| bayangan[b[0]][b[1] + 1] == 1) {
										return false;
									} else {
										return true;
									}
								} else {
									if (bayangan[a[0]][a[1] + 1] == 1
											|| bayangan[b[0]][b[1] + 1] == 1
											|| bayangan[d[0]][d[1] + 1] == 1
											|| bayangan[c[0]][c[1] + 1] == 1) {
										return false;
									} else {
										return true;
									}
								}
							}
						}
					}
				}
			}
		} else {
			return false;
		}
	}

	public void turun() {
		if (X1[0] < 17 && X2[0] < 17 && X3[0] < 17 && X4[0] < 17
				&& bisaTurun(X1, X2, X3, X4)) {
			newBalok = false;
			if (board[X1[0]][X1[1]].getDrawable() != null) {
				tileColor = board[X1[0]][X1[1]].getBackground();
			}
			bayangan[X1[0]][X1[1]] = 0;
			bayangan[X2[0]][X2[1]] = 0;
			bayangan[X3[0]][X3[1]] = 0;
			bayangan[X4[0]][X4[1]] = 0;
			board[X1[0]][X1[1]].setBackgroundResource(R.drawable.imgview);
			board[X2[0]][X2[1]].setBackgroundResource(R.drawable.imgview);
			board[X3[0]][X3[1]].setBackgroundResource(R.drawable.imgview);
			board[X4[0]][X4[1]].setBackgroundResource(R.drawable.imgview);
			X1[0]++;
			X2[0]++;
			X3[0]++;
			X4[0]++;
			bayangan[X1[0]][X1[1]] = 1;
			bayangan[X2[0]][X2[1]] = 1;
			bayangan[X3[0]][X3[1]] = 1;
			bayangan[X4[0]][X4[1]] = 1;
			board[X1[0]][X1[1]].setBackground(tileColor);
			board[X2[0]][X2[1]].setBackground(tileColor);
			board[X3[0]][X3[1]].setBackground(tileColor);
			board[X4[0]][X4[1]].setBackground(tileColor);
		}
		if (!bisaTurun(X1, X2, X3, X4)
				&& (X1[0] == 0 || X2[0] == 0 || X3[0] == 0 || X4[0] == 0)) {
			gameOver = true;
		}
		if (!bisaTurun(X1, X2, X3, X4)) {
			mp.start();
			checkCleared();
		}
	}

	public void kekanan() {
		if (X1[1] < 8 && X2[1] < 8 && X3[1] < 8 && X4[1] < 8
				&& bisaKekanan(X1, X2, X3, X4) && bisaTurun(X1, X2, X3, X4)) {
			if (board[X1[0]][X1[1]].getDrawable() != null) {
				tileColor = board[X1[0]][X1[1]].getBackground();
			}
			bayangan[X1[0]][X1[1]] = 0;
			bayangan[X2[0]][X2[1]] = 0;
			bayangan[X3[0]][X3[1]] = 0;
			bayangan[X4[0]][X4[1]] = 0;
			board[X1[0]][X1[1]].setBackgroundResource(R.drawable.imgview);
			board[X2[0]][X2[1]].setBackgroundResource(R.drawable.imgview);
			board[X3[0]][X3[1]].setBackgroundResource(R.drawable.imgview);
			board[X4[0]][X4[1]].setBackgroundResource(R.drawable.imgview);
			X1[1]++;
			X2[1]++;
			X3[1]++;
			X4[1]++;
			bayangan[X1[0]][X1[1]] = 1;
			bayangan[X2[0]][X2[1]] = 1;
			bayangan[X3[0]][X3[1]] = 1;
			bayangan[X4[0]][X4[1]] = 1;
			board[X1[0]][X1[1]].setBackground(tileColor);
			board[X2[0]][X2[1]].setBackground(tileColor);
			board[X3[0]][X3[1]].setBackground(tileColor);
			board[X4[0]][X4[1]].setBackground(tileColor);
		}
	}

	public void kekiri() {
		if (X1[1] > 0 && X2[1] > 0 && X3[1] > 0 && X4[1] > 0
				&& bisaKekiri(X1, X2, X3, X4) && bisaTurun(X1, X2, X3, X4)) {
			if (board[X1[0]][X1[1]].getDrawable() != null) {
				tileColor = board[X1[0]][X1[1]].getBackground();
			}
			bayangan[X1[0]][X1[1]] = 0;
			bayangan[X2[0]][X2[1]] = 0;
			bayangan[X3[0]][X3[1]] = 0;
			bayangan[X4[0]][X4[1]] = 0;
			board[X1[0]][X1[1]].setBackgroundResource(R.drawable.imgview);
			board[X2[0]][X2[1]].setBackgroundResource(R.drawable.imgview);
			board[X3[0]][X3[1]].setBackgroundResource(R.drawable.imgview);
			board[X4[0]][X4[1]].setBackgroundResource(R.drawable.imgview);
			X1[1]--;
			X2[1]--;
			X3[1]--;
			X4[1]--;
			bayangan[X1[0]][X1[1]] = 1;
			bayangan[X2[0]][X2[1]] = 1;
			bayangan[X3[0]][X3[1]] = 1;
			bayangan[X4[0]][X4[1]] = 1;
			board[X1[0]][X1[1]].setBackground(tileColor);
			board[X2[0]][X2[1]].setBackground(tileColor);
			board[X3[0]][X3[1]].setBackground(tileColor);
			board[X4[0]][X4[1]].setBackground(tileColor);
		}
	}

	public void puter() {
		if (bisaKekanan(X1, X2, X3, X4) && bisaKekiri(X1, X2, X3, X4)
				&& bisaTurun(X1, X2, X3, X4)) {
			if (board[X1[0]][X1[1]].getDrawable() != null) {
				tileColor = board[X1[0]][X1[1]].getBackground();
			}
			int minrow = Math.min(X1[0],
					Math.min(X2[0], Math.min(X3[0], X4[0])));
			int mincol = Math.min(X1[1],
					Math.min(X2[1], Math.min(X3[1], X4[1])));
			int[] temp1 = { X1[0] - minrow, X1[1] - mincol };
			int[] temp2 = { X2[0] - minrow, X2[1] - mincol };
			int[] temp3 = { X3[0] - minrow, X3[1] - mincol };
			int[] temp4 = { X4[0] - minrow, X4[1] - mincol };
			bayangan[X1[0]][X1[1]] = 0;
			bayangan[X2[0]][X2[1]] = 0;
			bayangan[X3[0]][X3[1]] = 0;
			bayangan[X4[0]][X4[1]] = 0;

			board[X1[0]][X1[1]].setBackgroundResource(R.drawable.imgview);
			board[X2[0]][X2[1]].setBackgroundResource(R.drawable.imgview);
			board[X3[0]][X3[1]].setBackgroundResource(R.drawable.imgview);
			board[X4[0]][X4[1]].setBackgroundResource(R.drawable.imgview);
			X1[0] = minrow + (2 - temp1[1]);
			X2[0] = minrow + (2 - temp2[1]);
			X3[0] = minrow + (2 - temp3[1]);
			X4[0] = minrow + (2 - temp4[1]);
			X1[1] = mincol + temp1[0];
			X2[1] = mincol + temp2[0];
			X3[1] = mincol + temp3[0];
			X4[1] = mincol + temp4[0];
			bayangan[X1[0]][X1[1]] = 1;
			bayangan[X2[0]][X2[1]] = 1;
			bayangan[X3[0]][X3[1]] = 1;
			bayangan[X4[0]][X4[1]] = 1;
			board[X1[0]][X1[1]].setBackground(tileColor);
			board[X2[0]][X2[1]].setBackground(tileColor);
			board[X3[0]][X3[1]].setBackground(tileColor);
			board[X4[0]][X4[1]].setBackground(tileColor);
		}
	}

	private void shiftDown(int startRow) {
		for (int row = startRow; row >0; row--) {
			for (int col = 0; col < 9; col++) {
				tileColor = board[row-1][col].getBackground();
				board[row-1][col].setBackgroundResource(R.drawable.imgview);
				board[row][col].setBackground(tileColor);
				bayangan[row][col] = bayangan[row - 1][col];
			}
		}
	}

	public boolean checkCleared() {
		boolean cleared = true;
		boolean isChanged = false;
		for (int i = 17; i > 0; i--) {
			cleared = true;
			for (int j = 0; j < 9; j++) {
				if (bayangan[i][j] == 0)
					cleared = false;
			}
			if (cleared) {
				isChanged = true;
				this.shiftDown(i);
				score += 9;
				// this.draw();
				i++;
			}
		}
		if (!isChanged) {
			newBalok = true;
		}
		return cleared;
	}

	public void setMedia(MediaPlayer media) {
		this.mp = media;
	}
}