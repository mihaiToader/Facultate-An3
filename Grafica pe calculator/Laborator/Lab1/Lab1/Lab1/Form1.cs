using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
           
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            System.Drawing.Graphics Drept;
            Drept = this.CreateGraphics();
            Pen myPen = new Pen(System.Drawing.Color.RoyalBlue);
            Rectangle myRectangle = new Rectangle(20, 20, 350 - 20, 250 - 20);
            Drept.DrawRectangle(myPen, myRectangle);
        }

        private void button2_Click_1(object sender, EventArgs e)
        {
            System.Drawing.Graphics Linie;
            Linie = this.CreateGraphics();
            Pen myPen = new Pen(System.Drawing.Color.Blue, 2);
            Linie.DrawLine(myPen, 20, 20, 350, 250);
        }

        private void button3_Click_1(object sender, EventArgs e)
        {
            System.Drawing.Graphics graphicsObj;
            graphicsObj = this.CreateGraphics();
            Font myFont = new System.Drawing.Font("Helvetica", 20, FontStyle.Italic);
            Brush myBrush = new SolidBrush(System.Drawing.Color.DarkBlue);
            graphicsObj.DrawString("Exemplu", myFont, myBrush, 175, 50);
        }
    }
}
