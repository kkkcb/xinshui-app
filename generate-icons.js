const fs = require('fs');
const path = require('path');

const sizes = [48, 72, 96, 144, 192, 512];
const colors = {
    background: ['#ff6b9d', '#ff8fab'],
    text: '#ffffff'
};

function generateSVGIcon(size) {
    const gradientId = `grad${size}`;
    return `<?xml version="1.0" encoding="UTF-8"?>
<svg width="${size}" height="${size}" viewBox="0 0 ${size} ${size}" xmlns="http://www.w3.org/2000/svg">
  <defs>
    <linearGradient id="${gradientId}" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" style="stop-color:#ff6b9d;stop-opacity:1" />
      <stop offset="100%" style="stop-color:#ff8fab;stop-opacity:1" />
    </linearGradient>
  </defs>
  <rect width="${size}" height="${size}" fill="url(#${gradientId})" rx="${size * 0.15}"/>
  <text x="50%" y="50%" dominant-baseline="middle" text-anchor="middle" 
        font-size="${size * 0.5}" font-family="Arial, sans-serif" 
        fill="white">💗</text>
</svg>`;
}

sizes.forEach(size => {
    const svg = generateSVGIcon(size);
    const filename = path.join(__dirname, `icon-${size}.svg`);
    fs.writeFileSync(filename, svg);
    console.log(`Generated ${filename}`);
});

console.log('Icons generated successfully!');
console.log('Note: You need to convert SVG files to PNG using an online converter or tool like ImageMagick');
console.log('Or use the icon-generator.html file in your browser');
