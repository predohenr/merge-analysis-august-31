package imagedata

import (
	"fmt"
	"net/http"

	"github.com/imgproxy/imgproxy/v3/fetcher"
	"github.com/imgproxy/imgproxy/v3/ierrors"
)

type FileSizeError struct{}

func newFileSizeError() error {
	return ierrors.Wrap(
		FileSizeError{},
		1,
		ierrors.WithStatusCode(http.StatusUnprocessableEntity),
		ierrors.WithPublicMessage("Invalid source image"),
		ierrors.WithShouldReport(false),
	)
}

func wrapDownloadError(err error, desc string) error {
	return ierrors.Wrap(
		fetcher.WrapError(err), 0,
		ierrors.WithPrefix(fmt.Sprintf("can't download %s", desc)),
	)
}

func newImageResponseStatusError(status int, body string) error {
	var msg string

	if len(body) > 0 {
		msg = fmt.Sprintf("Status: %d; %s", status, body)
	} else {
		msg = fmt.Sprintf("Status: %d", status)
	}

	statusCode := 404
	if status >= 400 && status < 500 {
		statusCode = status
	} else if status >= 500 {
		statusCode = http.StatusBadGateway
	}

	return ierrors.Wrap(
		ImageResponseStatusError(msg),
		1,
		ierrors.WithStatusCode(statusCode),
		ierrors.WithPublicMessage(msgSourceImageIsUnreachable),
		ierrors.WithShouldReport(false),
	)
}

func (e FileSizeError) Error() string { return "Source image file is too big" }
