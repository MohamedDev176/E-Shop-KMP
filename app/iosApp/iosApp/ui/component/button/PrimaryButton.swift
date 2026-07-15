import SwiftUI

struct PrimaryButton: View {
    let text: String
    let onClick: () -> Void

    private let primaryColor = Color(red: 0.8588235294, green: 0.1882352941, blue: 0.1333333333)

    var body: some View {
        Button(action: onClick) {
            Text(text)
                .font(.system(size: 18, weight: .bold))
                .kerning(0.8)
                .foregroundColor(.white)
                .frame(maxWidth: .infinity, maxHeight: .infinity)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 64)
        .background(primaryColor)
        .clipShape(RoundedRectangle(cornerRadius: 36, style: .continuous))
        .shadow(color: .black.opacity(0.18), radius: 8)
    }
}

struct PrimaryButton_Preview : PreviewProvider{
    static var previews: some View {
        PrimaryButton(text: "Click Here", onClick: {})
            .previewLayout(.sizeThatFits)
            .padding()
    }
}