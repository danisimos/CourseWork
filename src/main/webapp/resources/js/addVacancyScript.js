$(document).ready(function () {
    let form = $("#add-post-form")
    let postsList = $("#post-list")
    form.on('submit', function () {
        let content = form.find("#content").val();
        if (content === '') {
            return false
        }
        $.ajax("/account/addMyVacancy", {
            method: "POST",
            data: "content=" + content,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            success: function (data) {
                form.find("#content").val("")
                postsList.append("<div class=\"col-8\">\n" +
                    "                    <div class=\"card-deck mb-3 text-center\">\n" +
                    "                        <div class=\"card mb-4 box-shadow\">\n" +
                    "\n" +
                    "                            <div class=\"card-body\">\n" +
                    "                                <h3 class=\"card-title pricing-card-title\">" + content + "</h3>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>")
            }
        })
        return false
    })
})