import { CommonModule } from '@angular/common';
import { AfterViewInit, Component } from '@angular/core';
import { Chart } from 'chart.js/auto';
import ChartDataLabels from 'chartjs-plugin-datalabels'; // Import plugin

Chart.register(ChartDataLabels); // Đăng ký plugin đúng cách trong Chart.js

@Component({
  selector: 'app-bieudo',
  imports: [CommonModule],
  templateUrl: './bieudo.component.html',
  styleUrls: ['./bieudo.component.css'],
})
export class BieudoComponent implements AfterViewInit {
  ngAfterViewInit() {
    this.createRankingChart();
    this.createPieChart();
  }

  createRankingChart() {
    const ctx = document.getElementById('rankingChart') as HTMLCanvasElement;

    new Chart(ctx, {
      type: 'line', // Change to line chart
      data: {
        labels: [
          'Jan',
          'Feb',
          'Mar',
          'Apr',
          'May',
          'Jun',
          'Jul',
          'Aug',
          'Sep',
          'Oct',
          'Nov',
          'Dec',
        ], // Labels for 12 months
        datasets: [
          {
            label: 'Views Of Month',
            data: [12, 19, 7, 11, 15, 9, 8, 14, 10, 13, 17, 20], // Data for each month
            backgroundColor: 'rgba(75, 192, 192, 0.4)', // Area under the line (optional)
            borderColor: 'rgba(75, 192, 192, 1)', // Line color
            borderWidth: 3, // Line thickness
            fill: true, // Fill area under the line
            pointBackgroundColor: 'rgba(75, 192, 192, 1)', // Point color
            pointBorderColor: '#fff', // Border color for points
            pointRadius: 5, // Radius of data points
            pointHoverRadius: 7, // Hover size for data points
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            ticks: {
              color: 'black', // Set label color for x-axis (months)
              font: {
                size: 14,
              },
            },
          },
          y: {
            beginAtZero: true, // Start y-axis from 0
            ticks: {
              color: 'black', // Set number color on y-axis
              font: {
                size: 14,
              },
            },
          },
        },
        plugins: {
          legend: {
            display: true, // Show legend for dataset
            position: 'top', // Place legend at the top
          },
          datalabels: {
            display: true,
            color: 'black', // Color for data labels
            font: {
              size: 12, // Font size for data labels
            },
            anchor: 'end', // Position data labels at the top of points
            align: 'top', // Align labels above the points
          },
        },
      },
    });
  }

  createPieChart() {
    const ctx = document.getElementById('pieChart') as HTMLCanvasElement;

    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: [
          'Pop',
          'Rock',
          'Hip-Hop/Rap',
          'Jazz',
          'Classical',
          'Country',
          'Electronic',
          'Blues',
          'Folk',
          'R&B/Soul',
        ],
        datasets: [
          {
            data: [12, 19, 3, 66, 5,99,3,2,5,0],
            backgroundColor: [
              'rgba(255, 0, 0, 0.6)', // Red
              'rgba(0, 255, 0, 0.6)', // Green
              'rgba(0, 0, 255, 0.6)', // Blue
              'rgba(255, 255, 0, 0.6)', // Yellow
              'rgba(0, 255, 255, 0.6)', // Cyan
              'rgba(255, 0, 255, 0.6)', // Magenta
              'rgba(255, 165, 0, 0.6)', // Orange
              'rgba(128, 0, 128, 0.6)', // Purple
              'rgba(255, 192, 203, 0.6)', // Pink
              'rgba(128, 128, 128, 0.6)', //Gray
            ],
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: true, // Hiển thị legend
            position: 'left', // Đặt legend sang bên phải
            labels: {
              color: 'black', // Màu sắc của text trong legend
              font: {
                size: 15, // Cỡ chữ
              },
            },
          },
          datalabels: {
            display: false,
            color: 'black',
            font: {
              size: 15,
            },
            formatter: (value: string) => {
              return value + '%'; // Hiển thị phần trăm trên biểu đồ
            },
          },
        },
      },
    });
  }
}
