const mapStatus = (status) => {
    switch (status) {
        case "OPEN":
            return "primary"
        case "IN_PROGRESS":
            return "warning"
        case "CLOSED":
            return "danger"
        case "RESOLVED":
            return "success"
        case "REOPENED":
            return "secondary"
        default:
            return "primary"
    }
}

export default mapStatus;