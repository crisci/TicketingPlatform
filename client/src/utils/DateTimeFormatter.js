const formatDateTime = (dateTime) => {
    const inputDate = new Date("2023-06-29T15:29:36.654392");
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
    console.log(formattedDate);
    return formattedDate
}

export default formatDateTime;