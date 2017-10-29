using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Grafica_1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        class Punct
        {
            public double x, y;
            public Punct(double X, double Y) { x = X; y = Y; }
        }

        class muchie { public int st, dr; } // Pot fi si caracteristici: Culoare, TipLinie(…)
        class varf
        {
            public double x, y, z;
            public varf(int X, int Y, int Z) { x = X; y = Y; z = Z; }
        }

        int u1, v1, u2, v2; // ViewPort - Fereastra Ecran
        double a, b, c, d; // Window - Fereastra Reala
        int u(double x) { return (int)((x - a) / (b - a) * (u2 - u1) + u1); }
        int v(double y) { return (int)((y - d) / (c - d) * (v2 - v1) + v1); }
        double u_1(int u) { return (u - u1) * (b - a) / (u2 - u1) + a; }
        double v_1(int v) { return (v - v1) * (c - d) / (v2 - v1) + d; }
        void ViewPort(int x1, int y1, int x2, int y2) { u1 = x1; v1 = y1; u2 = x2; v2 = y2; }
        void Window(double x1, double y1, double x2, double y2) { a = x1; d = y1; b = x2; c = y2; }

        int Tip; double Raza, Alfa;
        void DefPr(double r, double a) { Raza = r; Alfa = a; } // r=1; a=0.8; // = Pi/4 
        double PrX(double x, double z) { return x + Raza * z * Math.Cos(Alfa); }
        double PrY(double y, double z) { return y + Raza * z * Math.Sin(Alfa); }
        double Px(varf P) { return PrX(P.x, P.z); }
        double Py(varf P) { return PrY(P.y, P.z); }

        List<Punct> puncte;
        void Segm(System.Drawing.Graphics Linie, Pen Pen, Punct P, Punct Q) // Segm. PQ
        {
            Linie.DrawLine(Pen, u(P.x), v(P.y), u(Q.x), v(Q.y));
        }

        void OutTextxy(System.Drawing.Graphics Mes, string s, Font myFont, Brush myBrush, Punct M)
        {
            Mes.DrawString(s, myFont, myBrush, u(M.x) - 10, v(M.y) - 25);
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Invalidate();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            ViewPort(50, 50, 650, 500);
            double Pi = 3.1416; DefPr(1, 3.14 / 4);
            System.Drawing.Pen myPen;
            myPen = new System.Drawing.Pen(System.Drawing.Color.Chocolate);
            System.Drawing.Graphics formGraphics = this.CreateGraphics();

            openFileDialog1.ShowDialog();
            System.IO.StreamReader Fc = new System.IO.StreamReader(openFileDialog1.FileName); // Cit. Corp.
            String Line = Fc.ReadLine();
            String[] Split = Line.Split(new Char[] { ' ', ',', '\t' });
            int n = Convert.ToInt32(Split[0]); varf[] V = new varf[n + 1];
            for (int i = 1; i <= n; i++) // Cit. Vf.
            {
                Line = Fc.ReadLine();
                Split = Line.Split(new Char[] { ' ', ',', '\t' });
                int X = Convert.ToInt32(Split[0]);
                int Z = Convert.ToInt32(Split[1]);
                int Y = Convert.ToInt32(Split[2]) - 100; // y <--->z
                V[i] = new varf(X, Y, Z); // V V V !!
            }
            Line = Fc.ReadLine();
            Split = Line.Split(new Char[] { ' ', ',', '\t' });
            int m = Convert.ToInt32(Split[0]); muchie[] M = new muchie[m + 1];
            for (int j = 1; j <= m; j++) // Cit. Muchii
            {
                Line = Fc.ReadLine();
                Split = Line.Split(new Char[] { ' ', ',', '\t' });
                M[j] = new muchie();
                M[j].st = Convert.ToInt32(Split[0]);
                M[j].dr = Convert.ToInt32(Split[1]);
            }
            Line = Fc.ReadLine(); // Cit. Car. Pr. Tip, r, α
            Split = Line.Split(new Char[] { ' ', ',', '\t' });
            Tip = Convert.ToInt32(Split[0]);
            Raza = Convert.ToDouble(Split[1]);
            Alfa = Convert.ToDouble(Split[2]);
            Fc.Close();

            DefPr(Raza, Alfa); // 1=Par(r,α), 2=Persp.(d,q);
            a = b = Px(V[1]); c = d = Py(V[1]);
            for (int i = 2; i <= n; i++)
            {
                double px = Px(V[i]);
                if (px < a) a = px; else if (px > b) b = px;
                double py = Py(V[i]);
                if (py < c) c = py; else if (py > d) d = py;
            }
            Window(a, d, b, c); // Fereasta Reală

            for (int j = 1; j <= m; j++) // Desenare muchii
                formGraphics.DrawLine(myPen, u(Px(V[M[j].st])), v(Py(V[M[j].st])),
                u(Px(V[M[j].dr])), v(Py(V[M[j].dr])));
            myPen.Dispose();
            formGraphics.Dispose();

        }

        private void button5_Click(object sender, EventArgs e)
        {
            List<Punct> graph = graphPoints();
            ViewPort(75, 50, 275, 250);
            Window(Min("x", graph), Max("y", graph),
            Max("x", graph), Min("y", graph)); // (a,d, b,c)
            System.Drawing.Graphics Plot;
            Plot = this.CreateGraphics();

            Pen myBPen = new Pen(Color.Black, 1);
            if (a * b < 0)
            {
                Punct P = new Punct(0, Max("y", graph));
                Punct Q = new Punct(0, Min("y", graph)); // Oy
                Segm(Plot, myBPen, P, Q);
            }
            if (c * d < 0)
            {
                Punct P = new Punct(a, 0);
                Punct Q = new Punct(b, 0); // Ox
                Segm(Plot, myBPen, P, Q);
            }
            Pen myPen = new Pen(System.Drawing.Color.Blue, 2);
            for (int i = 0; i < graph.Count - 1; i++)
            {
                Segm(Plot, myPen, graph[i], graph[i+1]);
            }
            
        }


        private double function(double x)
        {
            //return Math.Cos(x);
            //return Math.Sin(x);
            return x * x * x - 27;
            //return x * x;
            //return -x;
        }

        private List<Punct> graphPoints()
        {
            puncte = new List<Punct>();
            for (double x = -5.0; x <=5.0; x += 0.1)
            {
                puncte.Add(new Punct(x, function(x))); //function
            }
            return puncte;
        }

        private double Min(String which, List<Punct> puncte)
        {
            double min = Double.MaxValue;
            for (int i=0; i <puncte.Count; i++)
            {
                if (which=="x")
                {
                    min = Math.Min(min, puncte[i].x);
                }
                else
                {
                    min = Math.Min(min, puncte[i].y);
                }
            }
            return min;
        }

        private double Max(String which, List<Punct> puncte)
        {
            double max = Double.MinValue;
            for (int i = 0; i < puncte.Count; i++)
            {
                if (which == "x")
                {
                    max = Math.Max(max, puncte[i].x);
                }
                else
                {
                    max = Math.Max(max, puncte[i].y);
                }
            }
            return max;
        }

        private void Form1_MouseClick(object sender, MouseEventArgs e)
        {
            int u_ = e.X, v_ = e.Y;
            System.Drawing.Graphics Punct; Punct = this.CreateGraphics();
            Pen myPen = new Pen(Color.Red, 1);
            double x = u_1(u_), y = v_1(v_);
            Rectangle myRectangle = new Rectangle(u(x) - 2, v(y) - 2, 4, 4);
            Punct.DrawRectangle(myPen, myRectangle);

            double xx = (double)((int)(x * 100 + 0.5)) / 100;
            label2.Text = "x=" + xx.ToString();
            double yy = (double)((int)(y * 100 + 0.5)) / 100;
            label3.Text = "y=" + yy.ToString();

            if (function(xx) == yy)
            {
                label4.Text = "Punctele" + xx + " si " + yy + " apartin graficului functiei!";
            }
        }

    }


}
