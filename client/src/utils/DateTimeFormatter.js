const formatDateTime = (dateTime) => {
    const inputDate = new Date(dateTime);
    const monthNames = [
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];
    const month = monthNames[inputDate.getMonth()];
    const day = String(inputDate.getDate()).padStart(2, '0');
    const year = inputDate.getFullYear();
    const hours = String(inputDate.getHours()).padStart(2, '0');
    const minutes = String(inputDate.getMinutes()).padStart(2, '0');

    const formattedDate = `${month} ${day}, ${year} ${hours}:${minutes}`;
    return formattedDate
}

export default formatDateTime;