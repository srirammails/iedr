function mOver() {
	switch (this.nodeName.toLowerCase()) {
		case "tr":
			this.style.backgroundColor = '#cdf2cc'; /* light green color */
			break;
	}
}
function mOut() {
	switch (this.nodeName.toLowerCase()) {
		case "tr":
			this.style.backgroundColor = '#f9f9f9'; /* very light gray color for page background */
			break;
	}
}
